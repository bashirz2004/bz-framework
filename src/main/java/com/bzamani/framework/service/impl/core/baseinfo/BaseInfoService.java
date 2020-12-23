package com.bzamani.framework.service.impl.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.baseinfo.BaseInfoHeader;
import com.bzamani.framework.repository.core.baseinfo.IBaseInfoRepository;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoHeaderService;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoService;
import com.bzamani.framework.service.impl.core.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseInfoService extends GenericService<BaseInfo, Long> implements IBaseInfoService {
    @Autowired
    IBaseInfoRepository iBaseInfoRepository;

    @Autowired
    IBaseInfoHeaderService iBaseInfoHeaderService;

    @Override
    protected JpaRepository<BaseInfo, Long> getGenericRepo() {
        return iBaseInfoRepository;
    }

    @Override
    public List<BaseInfo> getAllByHeaderId(long headerId) {
        return iBaseInfoRepository.getAllByHeaderId(headerId);
    }

    @Override
    public List<BaseInfo> getAllByParentId(long parentId) {
        return iBaseInfoRepository.getAllByParentId(parentId);
    }

    @Override
    public String getAllHeadersAsXml() {
        return makeFirstLevelOfTree("");
    }

    public String makeFirstLevelOfTree(String returnValue) {
        List<BaseInfoHeader> headers = new ArrayList<BaseInfoHeader>();
        String imgName = "lockedstate.gif";
        headers = iBaseInfoHeaderService.getAllHeaders();
        for (BaseInfoHeader header : headers) {
            returnValue = returnValue + "<item text=\"" + header.getTitle() + "\" id=\"" + "H" + header.getId() + "\" im0=\"" + imgName + "\" im1=\"" + imgName + "\" im2=\"" + imgName + "\" >";
            returnValue = returnValue + "<item text=\"...\" im0=\"leaf.gif\" id=\"tH" + header.getId() + "\" /></item>";
        }
        return returnValue;
    }

    @Override
    public String getChildsAsXml(String id) {
        return makeTree(id, "");
    }

    public String makeTree(String id, String returnValue) {
        String qualifier = id.substring(0, 1);
        id = id.substring(1);
        if (isIdForBaseInformationHeader(qualifier)) {
            returnValue += makeBaseInformationTreeFirstLevelFromHeader(Long.valueOf(id));
        } else if (isIdForBaseInformation(qualifier)) {
            returnValue += makeBaseInformationTreeNextLevelFromBaseInformationId(Long.valueOf(id));
        }
        return returnValue;
    }

    private boolean isIdForBaseInformationHeader(String qualifier) {
        return qualifier.equalsIgnoreCase("H");
    }

    private boolean isIdForBaseInformation(String qualifier) {
        return qualifier.equalsIgnoreCase("B");
    }

    private String makeBaseInformationTreeFirstLevelFromHeader(Long headerId) {
        List<BaseInfo> children = iBaseInfoRepository.getAllByHeaderId(headerId);
        String result = "";
        for (BaseInfo baseInfo : children) {
            String imgName = "";
            if (baseInfo.getChildCount() > 0) {
                imgName = "folderClosed.gif";
                result = result + "<item text=\"" + baseInfo.getTitle() + "\" id=\"" + "B" + baseInfo.getId() + "\" im0=\"" + imgName + "\" im1=\"" + imgName + "\" im2=\"" + imgName + "\" ";
                result = result + " >";
                result = result + "<item text=\"...\" im0=\"leaf.gif\" id=\"t" + "B" + baseInfo.getId() + "\" />";
                result = result + "</item>";
                result = result + "/>";
            } else {
                imgName = "leaf.gif";
                result = result + "<item text=\"" + baseInfo.getTitle() + "\" id=\"" + "B" + baseInfo.getId() + "\" im0=\"" + imgName + "\" im1=\"" + imgName + "\" im2=\"" + imgName + "\" ";
                result = result + " >";
                result = result + "</item>";
                result = result + "/>";
            }
        }
        return result;
    }

    private String makeBaseInformationTreeNextLevelFromBaseInformationId(Long baseInfoId) {
        List<BaseInfo> children = iBaseInfoRepository.getAllByParentId(baseInfoId);
        String result = "";
        for (BaseInfo baseInfo : children) {
            String imgName = "";
            if (baseInfo.getChildCount() > 0) {
                imgName = "folderClosed.gif";
                result = result + "<item text=\"" + baseInfo.getTitle() + "\" id=\"" + "B" + baseInfo.getId() + "\" im0=\"" + imgName + "\" im1=\"" + imgName + "\" im2=\"" + imgName + "\" ";
                result = result + " >";
                result = result + "<item text=\"...\" im0=\"leaf.gif\" id=\"t" + "B" + baseInfo.getId() + "\" />";
                result = result + "</item>";
                result = result + "/>";

            } else {
                imgName = "leaf.gif";
                result = result + "<item text=\"" + baseInfo.getTitle() + "\" id=\"" + "B" + baseInfo.getId() + "\" im0=\"" + imgName + "\" im1=\"" + imgName + "\" im2=\"" + imgName + "\" ";
                result = result + " >";
                result = result + "</item>";
                result = result + "/>";
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> searchBaseInfo(String title, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<BaseInfo> baseInfos = new ArrayList<BaseInfo>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<BaseInfo> pageTuts;
        pageTuts = iBaseInfoRepository.searchBaseinfo(title, pagingSort);
        baseInfos = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", baseInfos);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    @Override
    public String reMakeTreeAfterAutoComplete(Long id) {
        String returnValue = "";
        List<Long> parents = new ArrayList<Long>();
        makeListOfParents(id, parents);
        for (int i = (parents.size() - 1); i >= 0; i--) {
            if (i == parents.size() - 1) { //header
                returnValue = makeFirstLevelOfTree(returnValue);
                String path = "<item text=\"...\" im0=\"leaf.gif\" id=\"tH" + parents.get(i).intValue() + "\" />";
                returnValue = returnValue.replace(path, customeMakeTree("H" + parents.get(i)));
            } else {//other
                String path = "<item text=\"...\" im0=\"leaf.gif\" id=\"tB" + parents.get(i).intValue() + "\" />";
                returnValue = returnValue.replace(path, customeMakeTree("B" + parents.get(i)));
            }
        }
        return returnValue;
    }

    public String customeMakeTree(String id) {
        String temp = "";
        List<BaseInfo> childs = new ArrayList<BaseInfo>();
        if (id.substring(0, 1).equals("H")) {
            childs = iBaseInfoRepository.getAllByHeaderId(Long.valueOf(id.substring(1)));
        } else if (id.substring(0, 1).equals("B")) {
            childs = iBaseInfoRepository.getAllByParentId(Long.valueOf(id.substring(1)));
        }
        String im0 = "leaf.gif";
        String im1 = "folderOpen.gif";
        String im2 = "folderClosed.gif";
        for (BaseInfo baseInfo : childs) {
            List<BaseInfo> childsOfChild = iBaseInfoRepository.getAllByParentId(baseInfo.getId());
            if (childsOfChild.size() > 0) {
                temp = temp + "<item text=\"" + baseInfo.getTitle() + "\" id=\"B" + baseInfo.getId() + "\" im0=\"" + im0 + "\" im1=\"" + im1 + "\" im2=\"" + im2 + "\" >";
                temp = temp + "<item text=\"...\" im0=\"leaf.gif\" id=\"tB" + baseInfo.getId() + "\" /></item>";
            } else {
                temp = temp + "<item text=\"" + baseInfo.getTitle() + "\" id=\"B" + baseInfo.getId() + "\" im0=\"" + im0 + "\" im1=\"" + im1 + "\" im2=\"" + im2 + "\" ></item>";
            }

        }
        if (childs.size() == 0) {
            BaseInfo baseInfo = loadByEntityId(Long.valueOf(id.substring(1)));
            im0 = im1 = im2 = "leaf.gif";
            temp = temp + "<item text=\"" + baseInfo.getTitle() + "\" id=\"" + "B" + baseInfo.getId() + "\" im0=\"" + im0 + "\" im1=\"" + im1 + "\" im2=\"" + im2 + "\" ></item>";
        }
        return temp;
    }

    public void makeListOfParents(Long baseInformationId, List<Long> parents) {
        BaseInfo baseInfo = loadByEntityId(baseInformationId);
        if (baseInfo.getParent() != null) {
            parents.add(baseInfo.getParent().getId());
            makeListOfParents(baseInfo.getParent().getId(), parents);
        } else {
            parents.add(baseInfo.getHeader().getId());
        }
    }

}
