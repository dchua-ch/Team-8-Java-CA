package sg.edu.iss.team8.leaveApp.login;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

//redirect user to different dashboards based on roles
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1,
			Authentication authentication) throws IOException, ServletException {

		boolean hasEmployeeRole = false;
		boolean hasAdminRole = false;
		boolean hasManagerRole = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("employee")) {
				hasEmployeeRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("admin")) {
				hasAdminRole = true;
				break;
			}
			else if (grantedAuthority.getAuthority().equals("manager")) {
				hasManagerRole = true;
				break;
			}
		}

		if (hasEmployeeRole || hasManagerRole) {
			redirectStrategy.sendRedirect(arg0, arg1, "/dashboard");
		} else if (hasAdminRole) {
			redirectStrategy.sendRedirect(arg0, arg1, "/adminDashboard");
		} else {
			throw new IllegalStateException();
		}
	}

}