import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { User } from '../../../features/iam/models/user.model';
import { AuthService } from '../../../features/iam/services/auth.service';
import { TranslateModule } from '@ngx-translate/core';

/**
 * @summary Navigation sidebar rendering role-based links for the authenticated user.
 */
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, CommonModule, TranslateModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  /**
   * @summary Stream of the current authenticated user.
   */
  currentUser$: Observable<User | null>;

  constructor(private authService: AuthService) {
    this.currentUser$ = this.authService.currentUser$;
  }

  isOwner(user: any): boolean {
    if (!user) return false;
    const role = user.role || (Array.isArray(user.roles) ? user.roles[0] : '');
    return role === 'ROLE_OWNER' || role === 'ROLE_ARRENDADOR' || 
           (Array.isArray(user.roles) && user.roles.some((r: string) => r === 'ROLE_OWNER' || r === 'ROLE_ARRENDADOR'));
  }

  isRenter(user: any): boolean {
    if (!user) return false;
    const role = user.role || (Array.isArray(user.roles) ? user.roles[0] : '');
    return role === 'ROLE_RENTER' || role === 'ROLE_ARRENDATARIO' || 
           (Array.isArray(user.roles) && user.roles.some((r: string) => r === 'ROLE_RENTER' || r === 'ROLE_ARRENDATARIO'));
  }
}
