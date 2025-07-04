package com.ncu.controller;

import com.healthsys.common.dao.PackageTestDAO;
import com.healthsys.common.model.PackageTest;
import com.healthsys.common.model.MedicalTest;

import java.util.List;

public class PackageTestController {
    private PackageTestDAO dao = new PackageTestDAO();

    // 添加项目到套餐
    public boolean addTestToPackage(Long packageId, Long testId) {
        return dao.addTestToPackage(packageId, testId);
    }

    // 从套餐中移除项目
    public boolean removeTestFromPackage(Long packageId, Long testId) {
        return dao.removeTestFromPackage(packageId, testId);
    }

    // 获取套餐中的所有项目
    public List<PackageTest> getTestsByPackage(Long packageId) {
        return dao.getTestsByPackage(packageId);
    }

    // 获取可添加的项目列表
    public List<MedicalTest> getAvailableTestsNotInPackage(Long packageId) {
        return dao.getAvailableTestsNotInPackage(packageId);
    }

    // 获取套餐包含的检查项目列表
    public List<MedicalTest> getMedicalTestsByPackage(Long packageId) {
        return dao.getMedicalTestsByPackage(packageId);
    }
}