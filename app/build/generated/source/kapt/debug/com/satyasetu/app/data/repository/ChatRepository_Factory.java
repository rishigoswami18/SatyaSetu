package com.satyasetu.app.data.repository;

import com.satyasetu.app.data.remote.OpenAIService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<OpenAIService> openAIServiceProvider;

  public ChatRepository_Factory(Provider<OpenAIService> openAIServiceProvider) {
    this.openAIServiceProvider = openAIServiceProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(openAIServiceProvider.get());
  }

  public static ChatRepository_Factory create(Provider<OpenAIService> openAIServiceProvider) {
    return new ChatRepository_Factory(openAIServiceProvider);
  }

  public static ChatRepository newInstance(OpenAIService openAIService) {
    return new ChatRepository(openAIService);
  }
}
