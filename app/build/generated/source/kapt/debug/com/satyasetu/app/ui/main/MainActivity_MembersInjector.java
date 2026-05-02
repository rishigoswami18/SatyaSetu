package com.satyasetu.app.ui.main;

import com.satyasetu.app.util.LocaleManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<LocaleManager> localeManagerProvider;

  public MainActivity_MembersInjector(Provider<LocaleManager> localeManagerProvider) {
    this.localeManagerProvider = localeManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<LocaleManager> localeManagerProvider) {
    return new MainActivity_MembersInjector(localeManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectLocaleManager(instance, localeManagerProvider.get());
  }

  @InjectedFieldSignature("com.satyasetu.app.ui.main.MainActivity.localeManager")
  public static void injectLocaleManager(MainActivity instance, LocaleManager localeManager) {
    instance.localeManager = localeManager;
  }
}
