/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.modules;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "xuly")
@NamedQueries({
    @NamedQuery(name = "_Processing.findAll", query = "SELECT _ FROM _Processing _"),
    @NamedQuery(name = "_Processing.findByMaXL", query = "SELECT _ FROM _Processing _ WHERE _.maXL = :maXL"),
    @NamedQuery(name = "_Processing.findByHinhThucXL", query = "SELECT _ FROM _Processing _ WHERE _.hinhThucXL = :hinhThucXL"),
    @NamedQuery(name = "_Processing.findBySoTien", query = "SELECT _ FROM _Processing _ WHERE _.soTien = :soTien"),
    @NamedQuery(name = "_Processing.findByNgayXL", query = "SELECT _ FROM _Processing _ WHERE _.ngayXL = :ngayXL"),
    @NamedQuery(name = "_Processing.findByTrangThaiXL", query = "SELECT _ FROM _Processing _ WHERE _.trangThaiXL = :trangThaiXL")})
public class _Processing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MaXL")
    private Integer maXL;
    @Column(name = "HinhThucXL")
    private String hinhThucXL;
    @Column(name = "SoTien")
    private Integer soTien;
    @Column(name = "NgayXL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayXL;
    @Column(name = "TrangThaiXL")
    private Integer trangThaiXL;
    @JoinColumn(name = "MaTV", referencedColumnName = "MaTV")
    @ManyToOne(optional = false)
    private _Member maTV;

    public _Processing() {
    }

    public _Processing(Integer maXL) {
        this.maXL = maXL;
    }

    public Integer getMaXL() {
        return maXL;
    }

    public void setMaXL(Integer maXL) {
        this.maXL = maXL;
    }

    public String getHinhThucXL() {
        return hinhThucXL;
    }

    public void setHinhThucXL(String hinhThucXL) {
        this.hinhThucXL = hinhThucXL;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }

    public Date getNgayXL() {
        return ngayXL;
    }

    public void setNgayXL(Date ngayXL) {
        this.ngayXL = ngayXL;
    }

    public Integer getTrangThaiXL() {
        return trangThaiXL;
    }

    public void setTrangThaiXL(Integer trangThaiXL) {
        this.trangThaiXL = trangThaiXL;
    }

    public _Member getMaTV() {
        return maTV;
    }

    public void setMaTV(_Member maTV) {
        this.maTV = maTV;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maXL != null ? maXL.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof _Processing)) {
            return false;
        }
        _Processing other = (_Processing) object;
        if ((this.maXL == null && other.maXL != null) || (this.maXL != null && !this.maXL.equals(other.maXL))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.modules._Processing[ maXL=" + maXL + " ]";
    }
    
}
