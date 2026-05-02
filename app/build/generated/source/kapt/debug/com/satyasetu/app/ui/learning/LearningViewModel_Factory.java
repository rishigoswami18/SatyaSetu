package com.satyasetu.app.ui.learning;

import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.LearningRepository;
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
public final class LearningViewModel_Factory implements Factory<LearningViewModel> {
  private final Provider<LearningRepository> learningRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public LearningViewModel_Factory(Provider<LearningRepository> learningRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.learningRepositoryProvider = learningRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public LearningViewModel get() {
    return newInstance(learningRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static LearningViewModel_Factory create(
      Provider<LearningRepository> learningRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new LearningViewModel_Factory(learningRepositoryProvider, authRepositoryProvider);
  }

  public static LearningViewModel newInstance(LearningRepository learningRepository,
      AuthRepository authRepository) {
    return new LearningViewModel(learningRepository, authRepository);
  }
}
