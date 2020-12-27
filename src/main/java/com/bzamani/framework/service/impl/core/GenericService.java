package com.bzamani.framework.service.impl.core;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.IGenericService;
import com.bzamani.framework.service.core.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public abstract class GenericService<T extends BaseEntity, PK> implements IGenericService<T, PK> {
    @Autowired
    IUserService iUserService;

    protected abstract JpaRepository<T, PK> getGenericRepo();

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    @Override
    @Transactional
    public T save(T t) {
        /* this section added only for control anonymous users that want to self register */
        User authenticatedUser = null;
        if (SecurityUtility.getAuthenticatedUser() != null)
            authenticatedUser = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername());
        if (t.getId() != null && t.getId() > 0) {
            t.setLastUpdateDate(new Date());
            t.setLastUpdater(authenticatedUser);
        } else {
            t.setCreateDate(new Date());
            t.setLastUpdateDate(t.getCreateDate());
            t.setCreator(authenticatedUser);
        }
        t.setIp(SecurityUtility.getRequestIp());
        return getGenericRepo().save(t);
    }

    @Override
    public T loadByEntityId(PK id) {
        Optional<T> t = getGenericRepo().findById(id);
        if (t.isPresent())
            return t.get();
        else
            return null;
    }

    @Override
    @Transactional
    public boolean deleteByEntityId(PK id) {
        getGenericRepo().deleteById(id);
        return true;
    }

    @Override
    public List<T> getAll(String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return getGenericRepo().findAll(Sort.by(orders));
    }

    @Override
    public Map<String, Object> getAllGrid(int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        List<T> list = new ArrayList<T>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        Page<T> pageTuts = getGenericRepo().findAll(pagingSort);
        list = pageTuts.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("entityList", list);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return response;
    }
}
