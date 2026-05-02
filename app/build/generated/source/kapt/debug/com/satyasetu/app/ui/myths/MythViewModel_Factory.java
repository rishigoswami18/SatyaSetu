package com.satyasetu.app.ui.myths;

import com.satyasetu.app.data.repository.MythRepository;
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
public final class MythViewModel_Factory implements Factory<MythViewModel> {
  private final Provider<MythRepository> mythRepositoryProvider;

  public MythViewModel_Factory(Provider<MythRepository> mythRepositoryProvider) {
    this.mythRepositoryProvider = mythRepositoryProvider;
  }

  @Override
  public MythViewModel get() {
    return newInstance(mythRepositoryProvider.get());
  }

  public static MythViewModel_Factory create(Provider<MythRepository> mythRepositoryProvider) {
    return new MythViewModel_Factory(mythRepositoryProvider);
  }

  public static MythViewModel newInstance(MythRepository mythRepository) {
    return new MythViewModel(mythRepository);
  }
}
