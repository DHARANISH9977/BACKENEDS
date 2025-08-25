package com.example.demo.Service;

import com.example.demo.Entity.Supplier;
import com.example.demo.exception.*;
import com.example.demo.Repository.SupplierRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier createSupplier(Supplier supplier) {
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new DuplicateEntityException("Supplier with this name already exists");
        }
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = getSupplierById(id);
        supplier.setContactPerson(supplierDetails.getContactPerson());
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.delete(getSupplierById(id));
    }
}