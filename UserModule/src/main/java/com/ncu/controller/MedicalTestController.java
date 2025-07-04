package com.ncu.controller;

import com.healthsys.common.dao.MedicalTestDAO;
import com.healthsys.common.model.MedicalTest;

import java.util.List;

public class MedicalTestController {
    private MedicalTestDAO medicalTestDAO;

    public MedicalTestController() {
        this.medicalTestDAO = new MedicalTestDAO();
    }

    public List<MedicalTest> getAllTests() {
        return medicalTestDAO.getAllTests();
    }

    public MedicalTest getTestById(Long id) {
        return medicalTestDAO.getTestById(id);
    }

    public boolean addTest(MedicalTest test) {
        return medicalTestDAO.addTest(test);
    }

    public boolean updateTest(MedicalTest test) {
        return medicalTestDAO.updateTest(test);
    }

    public boolean deleteTest(Long id) {
        return medicalTestDAO.deleteTest(id);
    }
}
