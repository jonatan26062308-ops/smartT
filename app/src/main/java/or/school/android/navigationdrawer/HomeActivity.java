package or.school.android.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import or.school.android.navigationdrawer.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

  private DrawerLayout drawerLayout;
  private NavigationView navigationView;
  private Toolbar toolbar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    setupToolbar();
    setupDrawer();
  }

  private void setupToolbar() {
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(findViewById(R.id.toolbar));
  }



  private void setupDrawer() {
    drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    drawerLayout.addDrawerListener(this);

    setupNavigationView();
  }

  private void setupNavigationView() {
  navigationView = findViewById(R.id.navigation_view);
  navigationView.setNavigationItemSelectedListener(this);
  setDefaultMenuItem();
  setupHeader();
}

  private void setDefaultMenuItem() {
    MenuItem menuItem = navigationView.getMenu().getItem(0);
    onNavigationItemSelected(menuItem);
    menuItem.setChecked(true);
  }

  private void setupHeader() {
    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_title).setOnClickListener(view -> Toast.makeText(
            HomeActivity.this,
            getString(R.string.title_click),
            Toast.LENGTH_SHORT).show());
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int title = getTitle(menuItem);
    /*
    if (menuItem.getItemId ()==R.id.nav_reg)
    {
      startActivity ( new Intent (HomeActivity.this, MainActivity.class) );
    }
    // כאן ניתן להוסיף אקטיביטיס נוספים
    else {

     */
      showFragment ( title );
      drawerLayout.closeDrawer ( GravityCompat.START );
   // }
    return true;
  }

  private int getTitle(@NonNull MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case R.id.nav_my_trips:
        return R.string.menu_my_trips;
      case R.id.nav_add_trip:
        return R.string.menu_add_trip;
      case R.id.nav_packing:
        return R.string.menu_packing;
      case R.id.nav_budget:
        return R.string.menu_budget;
      case R.id.nav_map:
        return R.string.menu_map;
      case R.id.nav_profile:
        return R.string.menu_profile;
      case R.id.nav_settings:
        return R.string.menu_settings;
      case R.id.nav_logout:
        logoutUser();
        return -1;
      default:
        throw new IllegalArgumentException("menu option not implemented!!");
    }
  }

  // Add this new method for logout functionality
  private void logoutUser() {
    FirebaseAuth.getInstance().signOut();
    startActivity(new Intent(HomeActivity.this, MainActivity.class));
    finish();
  }

  private void showFragment(@StringRes int titleId) {
    Fragment fragment;

    switch (titleId) {
      case R.string.menu_my_trips:
        // For now, use placeholder - we'll create TripListFragment later
        fragment = HomeContentFragment.newInstance(titleId);
        break;
      case R.string.menu_add_trip:
        fragment = new AddTripFragment(); // Your working trip form!
        break;
      case R.string.menu_packing:
        fragment = HomeContentFragment.newInstance(titleId); // Placeholder
        break;
      case R.string.menu_budget:
        fragment = HomeContentFragment.newInstance(titleId); // Placeholder
        break;
      case R.string.menu_map:
        fragment = HomeContentFragment.newInstance(titleId); // Placeholder
        break;
      case R.string.menu_profile:
        fragment = HomeContentFragment.newInstance(titleId); // Placeholder
        break;
      case R.string.menu_settings:
        fragment = HomeContentFragment.newInstance(titleId); // Placeholder
        break;
      default:
        fragment = HomeContentFragment.newInstance(titleId);
    }

    getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
            .replace(R.id.home_content, fragment)
            .commit();

    setTitle(getString(titleId));
  }

  @Override
  public void onDrawerSlide(@NonNull View view, float v) {
    //cambio en la posición del drawer
  }

  @Override
  public void onDrawerOpened(@NonNull View view) {
    //el drawer se ha abierto completamente
    Toast.makeText(this, getString(R.string.navigation_drawer_open),
            Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDrawerClosed(@NonNull View view) {
    //el drawer se ha cerrado completamente
  }

  @Override
  public void onDrawerStateChanged(int i) {
    //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
  }

}
