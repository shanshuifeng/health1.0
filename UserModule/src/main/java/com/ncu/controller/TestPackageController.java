package com.ncu.controller;

import com.healthsys.common.dao.MedicalTestDAO;
import com.healthsys.common.dao.TestPackageDAO;
import com.healthsys.common.dao.PackageTestDAO;
import com.healthsys.common.model.MedicalTest;
import com.healthsys.common.model.TestPackage;

import java.util.List;

public class TestPackageController {
    private TestPackageDAO testPackageDAO;
    private final MedicalTestDAO medicalTestDAO;
    private final PackageTestDAO packageTestDAO;

    public TestPackageController() {
        this.testPackageDAO = new TestPackageDAO();
        this.medicalTestDAO = new MedicalTestDAO();
        this.packageTestDAO = new PackageTestDAO(); // 确保 PackageTestDAO 已创建
    }

    public List<TestPackage> getAllPackages() {
        return testPackageDAO.getAllPackages();
    }

    public TestPackage getPackageById(Long id) {
        return testPackageDAO.getPackageById(id);
    }

    public boolean addPackage(TestPackage testPackage) {
        return testPackageDAO.addPackage(testPackage);
    }

    public boolean updatePackage(TestPackage testPackage) {
        return testPackageDAO.updatePackage(testPackage);
    }

    public boolean deletePackage(Long id) {
        return testPackageDAO.deletePackage(id);
    }

    // 新增获取套餐包含的项目列表的方法
    public List<MedicalTest> getTestsInPackage(Long packageId) {
        return packageTestDAO.getTestsByPackage(packageId).stream()
                .map(pt -> medicalTestDAO.getTestById(pt.getTestId()))
                .toList();
    }


    public boolean createCustomPackage(TestPackage testPackage, List<Long> testIds) {
        if (!testPackageDAO.addPackage(testPackage)) {
            return false;
        }

        for (Long testId : testIds) {
            if (!packageTestDAO.addTestToPackage(testPackage.getId(), testId)) {
                return false;
            }
        }

        return true;
    }


}
