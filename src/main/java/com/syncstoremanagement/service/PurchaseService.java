package com.syncstoremanagement.service;

import com.syncstoremanagement.database.dto.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    List<Long> sync(List<PurchaseDTO> listDTO);
}
