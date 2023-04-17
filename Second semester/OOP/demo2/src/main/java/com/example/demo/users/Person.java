package com.example.demo.users;

import com.example.demo.cards.Card;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "Person")
@Table(name = "bank_users", schema = "root")
public class Person implements UserDetails {
    public Person()
    {
        cards = null;
    }

    public Person(String name)
    {
        this.name = name;
        cards = null;
    }
    public Person(String name, String password)
    {
        this.name = name;
        this.password = password;
        cards = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column
    private String name;

    @OneToMany(targetEntity = Card.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "owners_name", referencedColumnName = "name")
    private List<Card> cards;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;
}
