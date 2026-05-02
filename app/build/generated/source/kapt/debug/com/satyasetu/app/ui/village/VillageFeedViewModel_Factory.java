package com.satyasetu.app.ui.village;

import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.BootstrapRepository;
import com.satyasetu.app.data.repository.PostRepository;
import com.satyasetu.app.data.repository.VillageRepository;
import com.satyasetu.app.util.LocaleManager;
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
public final class VillageFeedViewModel_Factory implements Factory<VillageFeedViewModel> {
  private final Provider<PostRepository> postRepositoryProvider;

  private final Provider<VillageRepository> villageRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<BootstrapRepository> bootstrapRepositoryProvider;

  private final Provider<LocaleManager> localeManagerProvider;

  public VillageFeedViewModel_Factory(Provider<PostRepository> postRepositoryProvider,
      Provider<VillageRepository> villageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<BootstrapRepository> bootstrapRepositoryProvider,
      Provider<LocaleManager> localeManagerProvider) {
    this.postRepositoryProvider = postRepositoryProvider;
    this.villageRepositoryProvider = villageRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.bootstrapRepositoryProvider = bootstrapRepositoryProvider;
    this.localeManagerProvider = localeManagerProvider;
  }

  @Override
  public VillageFeedViewModel get() {
    return newInstance(postRepositoryProvider.get(), villageRepositoryProvider.get(), authRepositoryProvider.get(), bootstrapRepositoryProvider.get(), localeManagerProvider.get());
  }

  public static VillageFeedViewModel_Factory create(Provider<PostRepository> postRepositoryProvider,
      Provider<VillageRepository> villageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<BootstrapRepository> bootstrapRepositoryProvider,
      Provider<LocaleManager> localeManagerProvider) {
    return new VillageFeedViewModel_Factory(postRepositoryProvider, villageRepositoryProvider, authRepositoryProvider, bootstrapRepositoryProvider, localeManagerProvider);
  }

  public static VillageFeedViewModel newInstance(PostRepository postRepository,
      VillageRepository villageRepository, AuthRepository authRepository,
      BootstrapRepository bootstrapRepository, LocaleManager localeManager) {
    return new VillageFeedViewModel(postRepository, villageRepository, authRepository, bootstrapRepository, localeManager);
  }
}
