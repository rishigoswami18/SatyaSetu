package com.satyasetu.app.ui.feed;

import com.satyasetu.app.data.repository.VideoRepository;
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
public final class FeedViewModel_Factory implements Factory<FeedViewModel> {
  private final Provider<VideoRepository> videoRepositoryProvider;

  public FeedViewModel_Factory(Provider<VideoRepository> videoRepositoryProvider) {
    this.videoRepositoryProvider = videoRepositoryProvider;
  }

  @Override
  public FeedViewModel get() {
    return newInstance(videoRepositoryProvider.get());
  }

  public static FeedViewModel_Factory create(Provider<VideoRepository> videoRepositoryProvider) {
    return new FeedViewModel_Factory(videoRepositoryProvider);
  }

  public static FeedViewModel newInstance(VideoRepository videoRepository) {
    return new FeedViewModel(videoRepository);
  }
}
