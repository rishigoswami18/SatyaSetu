package com.satyasetu.app.ui.village;

import android.location.Geocoder;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.VillageRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MapViewModel_Factory implements Factory<MapViewModel> {
  private final Provider<VillageRepository> villageRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<Geocoder> geocoderProvider;

  public MapViewModel_Factory(Provider<VillageRepository> villageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider, Provider<Geocoder> geocoderProvider) {
    this.villageRepositoryProvider = villageRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.geocoderProvider = geocoderProvider;
  }

  @Override
  public MapViewModel get() {
    return newInstance(villageRepositoryProvider.get(), authRepositoryProvider.get(), geocoderProvider.get());
  }

  public static MapViewModel_Factory create(Provider<VillageRepository> villageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider, Provider<Geocoder> geocoderProvider) {
    return new MapViewModel_Factory(villageRepositoryProvider, authRepositoryProvider, geocoderProvider);
  }

  public static MapViewModel newInstance(VillageRepository villageRepository,
      AuthRepository authRepository, Geocoder geocoder) {
    return new MapViewModel(villageRepository, authRepository, geocoder);
  }
}
