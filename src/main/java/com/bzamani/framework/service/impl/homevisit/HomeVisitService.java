package com.bzamani.framework.service.impl.homevisit;

import com.bzamani.framework.model.homevisit.HomeVisitRequest;
import com.bzamani.framework.repository.homevisit.IHomeVisitRepository;
import com.bzamani.framework.service.homevisit.IHomeVisitService;
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
public class HomeVisitService extends GenericService<HomeVisitRequest, Long> implements IHomeVisitService {
    @Autowired
    private IHomeVisitRepository iHomeVisitRepository;

    @Override
    protected JpaRepository<HomeVisitRequest, Long> getGenericRepo() {
        return iHomeVisitRepository;
    }


    @Override
    public Map<String, Object> search(String firstname, String lastname, String mobile,Integer requestType, Boolean done,
                                      int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<HomeVisitRequest> requests;
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<HomeVisitRequest> pageTuts = iHomeVisitRepository.search(firstname, lastname, mobile,requestType, done, pagingSort);
        requests = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", requests);
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


}
