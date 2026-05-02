package com.satyasetu.app.ui.village;

import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.PostRepository;
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
public final class CreatePostViewModel_Factory implements Factory<CreatePostViewModel> {
  private final Provider<PostRepository> postRepositoryProvider;

  private final Provider<VillageRepository> villageRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public CreatePostViewModel_Factory(Provider<PostRepository> postRepositoryProvider,
      Provider<VillageRepository> villageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.postRepositoryProvider = postRepositoryProvider;
    this.villageRepositoryProvider = villageRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public CreatePostViewModel get() {
    return newInstance(postRepositoryProvider.get(), villageRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static CreatePostViewModel_Factory create(Provider<PostRepository> postRepositoryProvider,
      Provider<VillageRepository> villageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new CreatePostViewModel_Factory(postRepositoryProvider, villageRepositoryProvider, authRepositoryProvider);
  }

  public static CreatePostViewModel newInstance(PostRepository postRepository,
      VillageRepository villageRepository, AuthRepository authRepository) {
    return new CreatePostViewModel(postRepository, villageRepository, authRepository);
  }
}
