import { HttpHeaders } from '@angular/common/http';
import { LoginService } from './../services/login.service';
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const rolesGuard: CanActivateFn = (route, state) => {

    const loginService = inject(LoginService);
    const router = inject(Router);

    const expectedRoles = route.data['roles'] as string[];
  if (!loginService.token) {
    return router.parseUrl('/login')
  }

  const userRoles = loginService.userRoles();
  const hasPermission = expectedRoles.some(role => userRoles.includes(role));
  if (hasPermission) {
      return true;
  }

  return router.parseUrl('/not-authorized');

};
