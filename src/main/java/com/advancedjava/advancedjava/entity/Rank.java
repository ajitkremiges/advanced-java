package com.advancedjava.advancedjava.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ranks")
public class Rank {

    public Rank(){
        
    }
    public Rank(int id) {
        this.id = (long) id;
    }

    @OneToMany(mappedBy = "rank")
    private List<Employee> employees;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rankdesc")
    private String rankDescription;

    @ManyToOne
    @JoinColumn(name = "parentrankid")
    private Rank parentRank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRankDescription() {
        return rankDescription;
    }

    public void setRankDescription(String rankDescription) {
        this.rankDescription = rankDescription;
    }

    public Rank getParentRank() {
        return parentRank;
    }

    public void setParentRank(Rank parentRank) {
        this.parentRank = parentRank;
    }

    public Rank(Long id, String rankDescription, Rank parentRank) {
        this.id = id;
        this.rankDescription = rankDescription;
        this.parentRank = parentRank;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Rank(List<Employee> employees, Long id, String rankDescription, Rank parentRank) {
        this.employees = employees;
        this.id = id;
        this.rankDescription = rankDescription;
        this.parentRank = parentRank;
    }
    
}
