package com.healthsys.common.controller;

import com.healthsys.common.dao.AppointmentDAO;
import com.healthsys.common.dao.MedicalTestDAO;
import com.healthsys.common.dao.TestPackageDAO;
import com.healthsys.common.model.Appointment;
import com.healthsys.common.model.MedicalTest;
import com.healthsys.common.model.TestPackage;
import com.healthsys.common.model.User;
import com.healthsys.common.util.DbUtil;
import com.healthsys.common.view.appointment.AppointmentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentController {
    private AppointmentDAO appointmentDAO;
    private TestPackageDAO packageDAO;
    private MedicalTestDAO testDAO;

    public AppointmentController() {
        this.appointmentDAO = new AppointmentDAO();
        this.packageDAO = new TestPackageDAO();
        this.testDAO = new MedicalTestDAO();
    }

    public Double getAppointmentPrice(Long appointmentId) {
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
        if (appointment == null)
            return null;

        Long packageId = appointment.getPackageId();
        if (packageId == null)
            return 0.0; // 如果是单项检查，后续可扩展

        TestPackage testPackage = packageDAO.getPackageById(packageId);
        if (testPackage == null)
            return null;

        return testPackage.getPrice();
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentDAO.getAppointmentById(appointmentId);
    }

    public boolean createAppointment(User user, Long packageId, Date appointmentTime) {
        Appointment appointment = new Appointment((long) user.getId(), packageId, appointmentTime);
        return appointmentDAO.createAppointment(appointment);
    }

    public List<Appointment> getUserAppointments(User user) {
        return appointmentDAO.getUserAppointments(user.getId());
    }

    public boolean cancelAppointment(long appointmentId) {
        return appointmentDAO.cancelAppointment(appointmentId);
    }

    public boolean completeAppointment(long appointmentId) {
        return appointmentDAO.completeAppointment(appointmentId);
    }

    public List<TestPackage> getAllPackages() {
        return packageDAO.getAllPackages();
    }

    public boolean createCustomPackage(TestPackage testPackage, List<Long> testIds) {
        return packageDAO.createPackage(testPackage, testIds);
    }

    public List<Appointment> getUserAppointmentsByStatus(long userId, String status) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE user_id = ? AND status = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getLong("user_id"),
                        rs.getObject("package_id") != null ? rs.getLong("package_id") : null,
                        new Date(rs.getTimestamp("appointment_time").getTime()));
                appointment.setId(rs.getLong("id"));
                appointment.setStatus(rs.getString("status"));
                appointment.setPaymentStatus(rs.getBoolean("payment_status"));

                if (rs.getTimestamp("exam_time") != null) {
                    appointment.setExamTime(new Date(rs.getTimestamp("exam_time").getTime()));
                }

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean updatePaymentStatus(Long appointmentId, boolean paid) {
        return appointmentDAO.updatePaymentStatus(appointmentId, paid);
    }

    public TestPackage getTestPackageById(Long packageId) {
        String sql = "SELECT * FROM test_packages WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, packageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TestPackage(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MedicalTest getMedicalTestById(Long testId) {
        String sql = "SELECT * FROM medical_tests WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, testId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MedicalTest(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("normal_range"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MedicalTest> getAllTests() {
        return testDAO.getAllTests();
    }

    private AppointmentView appointmentView;

    public void setAppointmentView(AppointmentView view) {
        this.appointmentView = view;
    }

    public void notifyAppointmentViewRefresh() {
        if (appointmentView != null) {
            appointmentView.refreshAppointmentData();
        }
    }
}