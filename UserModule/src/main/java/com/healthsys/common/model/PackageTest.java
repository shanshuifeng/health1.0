package com.healthsys.common.model;

public class PackageTest {
    private Long packageId;
    private Long testId;

    public PackageTest() {}

    public PackageTest(Long packageId, Long testId) {
        this.packageId = packageId;
        this.testId = testId;
    }

    // Getters and Setters
    public Long getPackageId() { return packageId; }
    public void setPackageId(Long packageId) { this.packageId = packageId; }

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }
}
