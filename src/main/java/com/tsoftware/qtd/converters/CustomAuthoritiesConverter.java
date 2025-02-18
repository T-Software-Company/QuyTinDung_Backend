package com.tsoftware.qtd.converters;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {

    Map<String, Object> realmAccessMap = source.getClaimAsMap("resource_access");
    var clientAccess = realmAccessMap.get("server");
    if (clientAccess instanceof Map<?, ?>) {
      var roles = ((Map<?, ?>) clientAccess).get("roles");
      if (roles instanceof List<?>) {
        return ((List<?>) roles)
            .stream()
                .map(s -> new SimpleGrantedAuthority(String.format("%s%s", "ROLE_", s)))
                .collect(Collectors.toList());
      }
    }
    return Collections.emptyList();
  }
}
