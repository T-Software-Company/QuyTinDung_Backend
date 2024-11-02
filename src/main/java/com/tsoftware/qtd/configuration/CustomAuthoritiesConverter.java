// Lớp này triển khai Converter để chuyển đổi một đối tượng JWT thành một tập hợp các quyền hạn (authorities).

package com.tsoftware.qtd.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    // Tên của claim "realm_access" chứa thông tin về các quyền hạn của người dùng trong JWT.
    private final String REALM_ACCESS = "realm_access";

    // Tiền tố "ROLE_" sẽ được thêm vào trước mỗi quyền để phù hợp với quy ước Spring Security.
    private final String ROLE_PREFIX = "ROLE_";

    // Tên của thuộc tính "roles" trong "realm_access" chứa danh sách các quyền.
    private final String ROLES = "roles";

    // Phương thức convert chuyển đổi đối tượng Jwt thành một tập hợp các GrantedAuthority.
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // Lấy đối tượng "realm_access" từ JWT claim dưới dạng một Map.
        Map<String, Object> realmAccessMap = source.getClaimAsMap(REALM_ACCESS);

        // Lấy đối tượng "roles" từ "realm_access".
        Object roles = realmAccessMap.get(ROLES);

        // Kiểm tra xem "roles" có phải là một danh sách hay không.
        // Nếu phải, chuyển đổi danh sách chuỗi thành các đối tượng SimpleGrantedAuthority.
        if (roles instanceof List stringRoles) {
            return ((List<String>) stringRoles)
                    .stream()
                    // Thêm tiền tố "ROLE_" vào mỗi quyền và chuyển đổi thành SimpleGrantedAuthority.
                    .map(s -> new SimpleGrantedAuthority(String.format("%s%s", ROLE_PREFIX, s)))
                    .collect(Collectors.toList());
        }

        // Nếu "roles" không phải là một danh sách, trả về một danh sách trống.
        return Collections.emptyList();
    }
}
