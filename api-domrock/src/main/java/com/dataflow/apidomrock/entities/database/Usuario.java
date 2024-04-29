package com.dataflow.apidomrock.entities.database;

import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String senha;
    private String token;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "cnpj_organizacao", nullable = false)
    private Organizacao organizacao;

    @ManyToMany(targetEntity = NivelAcesso.class, fetch = FetchType.EAGER)
    @JoinTable(name = "nivel_acesso_usuario", // nome da tabela no sql
            joinColumns = @JoinColumn(name = "id_usuario"), // fk do usuario na nivel_acesso_usuario
            inverseJoinColumns = @JoinColumn(name = "id_nivel") // fk do nivel na nivel_acesso_usuario
    )
    private List<NivelAcesso> niveisAcesso;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        for (NivelAcesso nivelAcesso : this.niveisAcesso){
            if (nivelAcesso.getNivel().equalsIgnoreCase(NivelAcessoEnum.MASTER.toString()))
                return List.of(new SimpleGrantedAuthority("UsuarioAcesso_Master"),
                        new SimpleGrantedAuthority("UsuarioAcesso_Full"),
                        new SimpleGrantedAuthority("UsuarioAcesso_LZ"),
                        new SimpleGrantedAuthority("UsuarioAcesso_BZ"),
                        new SimpleGrantedAuthority("UsuarioAcesso_SZ"));
            else if (nivelAcesso.getNivel().equalsIgnoreCase(NivelAcessoEnum.FULL.toString()))
                return List.of(new SimpleGrantedAuthority("UsuarioAcesso_Full"),
                        new SimpleGrantedAuthority("UsuarioAcesso_LZ"),
                        new SimpleGrantedAuthority("UsuarioAcesso_BZ"),
                        new SimpleGrantedAuthority("UsuarioAcesso_SZ"));
            else if (nivelAcesso.getNivel().equalsIgnoreCase(NivelAcessoEnum.LZ.toString()))
                return List.of(new SimpleGrantedAuthority("UsuarioAcesso_LZ"));
            else if (nivelAcesso.getNivel().equalsIgnoreCase(NivelAcessoEnum.B.toString()))
                return List.of(new SimpleGrantedAuthority("UsuarioAcesso_BZ"));
            else if (nivelAcesso.getNivel().equalsIgnoreCase(NivelAcessoEnum.S.toString()))
                return List.of(new SimpleGrantedAuthority("UsuarioAcesso_SZ"));
        }
        return null;
    }
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
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

}
