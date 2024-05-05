/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.modules;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "thanhvien")
@NamedQueries({
    @NamedQuery(name = "_Member.findAll", query = "SELECT _ FROM _Member _"),
    @NamedQuery(name = "_Member.findByMaTV", query = "SELECT _ FROM _Member _ WHERE _.maTV = :maTV"),
    @NamedQuery(name = "_Member.findByEmail", query = "SELECT _ FROM _Member _ WHERE _.email = :email"),
    @NamedQuery(name = "_Member.findByPassword", query = "SELECT _ FROM _Member _ WHERE _.password = :password"),
    @NamedQuery(name = "_Member.findByHoTen", query = "SELECT _ FROM _Member _ WHERE _.hoTen = :hoTen"),
    @NamedQuery(name = "_Member.findByKhoa", query = "SELECT _ FROM _Member _ WHERE _.khoa = :khoa"),
    @NamedQuery(name = "_Member.findByNganh", query = "SELECT _ FROM _Member _ WHERE _.nganh = :nganh"),
    @NamedQuery(name = "_Member.findBySdt", query = "SELECT _ FROM _Member _ WHERE _.sdt = :sdt")})
public class _Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MaTV")
    private String maTV;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Column(name = "HoTen")
    private String hoTen;
    @Column(name = "Khoa")
    private String khoa;
    @Column(name = "Nganh")
    private String nganh;
    @Column(name = "SDT")
    private String sdt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maTV")
    private Collection<_Processing> processingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maTV")
    private Collection<_UsageInformation> usageInformationCollection;

    public _Member() {
    }

    public _Member(String maTV) {
        this.maTV = maTV;
    }

    public _Member(String maTV, String email, String password, String hoTen) {
        this.maTV = maTV;
        this.email = email;
        this.password = password;
        this.hoTen = hoTen;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Collection<_Processing> getProcessingCollection() {
        return processingCollection;
    }

    public void setProcessingCollection(Collection<_Processing> processingCollection) {
        this.processingCollection = processingCollection;
    }

    public Collection<_UsageInformation> getUsageInformationCollection() {
        return usageInformationCollection;
    }

    public void setUsageInformationCollection(Collection<_UsageInformation> usageInformationCollection) {
        this.usageInformationCollection = usageInformationCollection;
    }

    public void checkMaTVFormat() throws Exception {
        if (maTV == null || maTV.length() != 10) {
            throw new Exception("Mã TV phải có độ dài 10 ký tự.");
        }
        try {
            Long.parseLong(maTV);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new Exception("Mã thành viên phải là số.");
        }
    }

    public static String checkLoggedIn(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        try {
            _Member loggedInMember = (_Member) session.getAttribute("loggedInMember");
            if (loggedInMember == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Phiên đăng nhập đã hết hạn !");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Phiên đăng nhập đã hết hạn !");
            return "redirect:/login";
        }
        return "";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maTV != null ? maTV.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof _Member)) {
            return false;
        }
        _Member other = (_Member) object;
        if ((this.maTV == null && other.maTV != null) || (this.maTV != null && !this.maTV.equals(other.maTV))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.modules._Member[ maTV=" + maTV + " ]";
    }

}
