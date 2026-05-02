package com.satyasetu.app.ui.tracker;

import com.satyasetu.app.data.repository.TrackerRepository;
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
public final class TrackerViewModel_Factory implements Factory<TrackerViewModel> {
  private final Provider<TrackerRepository> trackerRepositoryProvider;

  public TrackerViewModel_Factory(Provider<TrackerRepository> trackerRepositoryProvider) {
    this.trackerRepositoryProvider = trackerRepositoryProvider;
  }

  @Override
  public TrackerViewModel get() {
    return newInstance(trackerRepositoryProvider.get());
  }

  public static TrackerViewModel_Factory create(
      Provider<TrackerRepository> trackerRepositoryProvider) {
    return new TrackerViewModel_Factory(trackerRepositoryProvider);
  }

  public static TrackerViewModel newInstance(TrackerRepository trackerRepository) {
    return new TrackerViewModel(trackerRepository);
  }
}
