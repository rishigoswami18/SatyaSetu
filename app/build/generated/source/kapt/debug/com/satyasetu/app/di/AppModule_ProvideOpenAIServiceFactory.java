package com.satyasetu.app.di;

import com.satyasetu.app.data.remote.OpenAIService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideOpenAIServiceFactory implements Factory<OpenAIService> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideOpenAIServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public OpenAIService get() {
    return provideOpenAIService(retrofitProvider.get());
  }

  public static AppModule_ProvideOpenAIServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideOpenAIServiceFactory(retrofitProvider);
  }

  public static OpenAIService provideOpenAIService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOpenAIService(retrofit));
  }
}
