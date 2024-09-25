package org.example.hospital.service;


import org.example.hospital.convertors.LabTestConvertor;
import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.entity.LabTest;
import org.example.hospital.repository.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabTestService {
    private final LabTestRepository labTestRepository;
    private final LabTestConvertor labTestConvertor;

    @Autowired
    public LabTestService(LabTestRepository labTestRepository, LabTestConvertor labTestConvertor) {
        this.labTestRepository = labTestRepository;
        this.labTestConvertor = labTestConvertor;
    }

    @Transactional
    public void addLabTest(LabTestDTO labTestDTO)
    {
        LabTest labTest = labTestConvertor.convertToEntity(labTestDTO, new LabTest());

        labTestRepository.save(labTest);
    }
    public LabTest getLabTest(Long id)
    {
        if(labTestRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Lab test not found");
        }
        return labTestRepository.findById(id).get();
    }

    @Transactional
    public void updateLabTest(Long labTestId, LabTestDTO labTestDTO)
    {
        if(labTestRepository.findById(labTestId).isEmpty())
        {
            throw new NoSuchElementException("Lab test not found");
        }
        LabTest labTest = labTestRepository.findById(labTestId).get();

        labTest = labTestConvertor.convertToEntity(labTestDTO, labTest);

        labTestRepository.save(labTest);
    }

    @Transactional
    public void deleteLabTest(Long id)
    {
        if(labTestRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Lab test not found");
        }
        labTestRepository.deleteById(id);
    }

    public List<LabTest> getAllLabTests(){
        return labTestRepository.findAll();
    }



}