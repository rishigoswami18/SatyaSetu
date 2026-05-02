package com.satyasetu.app;

import android.app.Activity;
import android.app.Service;
import android.location.Geocoder;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.satyasetu.app.data.remote.OpenAIService;
import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.BootstrapRepository;
import com.satyasetu.app.data.repository.ChatRepository;
import com.satyasetu.app.data.repository.LearningRepository;
import com.satyasetu.app.data.repository.MythRepository;
import com.satyasetu.app.data.repository.PostRepository;
import com.satyasetu.app.data.repository.ReportRepository;
import com.satyasetu.app.data.repository.TrackerRepository;
import com.satyasetu.app.data.repository.VideoRepository;
import com.satyasetu.app.data.repository.VillageRepository;
import com.satyasetu.app.di.AppModule_ProvideFirebaseAuthFactory;
import com.satyasetu.app.di.AppModule_ProvideFirebaseDatabaseFactory;
import com.satyasetu.app.di.AppModule_ProvideFirebaseStorageFactory;
import com.satyasetu.app.di.AppModule_ProvideGeocoderFactory;
import com.satyasetu.app.di.AppModule_ProvideOkHttpClientFactory;
import com.satyasetu.app.di.AppModule_ProvideOpenAIServiceFactory;
import com.satyasetu.app.di.AppModule_ProvideRetrofitFactory;
import com.satyasetu.app.ui.admin.AdminViewModel;
import com.satyasetu.app.ui.admin.AdminViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.auth.AuthViewModel;
import com.satyasetu.app.ui.auth.AuthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.chatbot.ChatViewModel;
import com.satyasetu.app.ui.chatbot.ChatViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.feed.FeedViewModel;
import com.satyasetu.app.ui.feed.FeedViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.learning.LearningViewModel;
import com.satyasetu.app.ui.learning.LearningViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.main.MainActivity;
import com.satyasetu.app.ui.main.MainActivity_MembersInjector;
import com.satyasetu.app.ui.myths.MythViewModel;
import com.satyasetu.app.ui.myths.MythViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.report.ReportViewModel;
import com.satyasetu.app.ui.report.ReportViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.tracker.TrackerViewModel;
import com.satyasetu.app.ui.tracker.TrackerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.village.CreatePostViewModel;
import com.satyasetu.app.ui.village.CreatePostViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.village.MapViewModel;
import com.satyasetu.app.ui.village.MapViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.ui.village.VillageFeedViewModel;
import com.satyasetu.app.ui.village.VillageFeedViewModel_HiltModules_KeyModule_ProvideFactory;
import com.satyasetu.app.util.LocaleManager;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerSatyaSetuApp_HiltComponents_SingletonC {
  private DaggerSatyaSetuApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public SatyaSetuApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements SatyaSetuApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements SatyaSetuApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements SatyaSetuApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements SatyaSetuApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements SatyaSetuApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements SatyaSetuApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements SatyaSetuApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public SatyaSetuApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends SatyaSetuApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends SatyaSetuApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends SatyaSetuApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends SatyaSetuApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
      injectMainActivity2(arg0);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AdminViewModel_HiltModules_KeyModule_ProvideFactory.provide(), AuthViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ChatViewModel_HiltModules_KeyModule_ProvideFactory.provide(), CreatePostViewModel_HiltModules_KeyModule_ProvideFactory.provide(), FeedViewModel_HiltModules_KeyModule_ProvideFactory.provide(), LearningViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MapViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MythViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ReportViewModel_HiltModules_KeyModule_ProvideFactory.provide(), TrackerViewModel_HiltModules_KeyModule_ProvideFactory.provide(), VillageFeedViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectLocaleManager(instance, singletonCImpl.localeManagerProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends SatyaSetuApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AdminViewModel> adminViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<ChatViewModel> chatViewModelProvider;

    private Provider<CreatePostViewModel> createPostViewModelProvider;

    private Provider<FeedViewModel> feedViewModelProvider;

    private Provider<LearningViewModel> learningViewModelProvider;

    private Provider<MapViewModel> mapViewModelProvider;

    private Provider<MythViewModel> mythViewModelProvider;

    private Provider<ReportViewModel> reportViewModelProvider;

    private Provider<TrackerViewModel> trackerViewModelProvider;

    private Provider<VillageFeedViewModel> villageFeedViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.adminViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.chatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.createPostViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.feedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.learningViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.mapViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.mythViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.reportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.trackerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.villageFeedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(11).put("com.satyasetu.app.ui.admin.AdminViewModel", ((Provider) adminViewModelProvider)).put("com.satyasetu.app.ui.auth.AuthViewModel", ((Provider) authViewModelProvider)).put("com.satyasetu.app.ui.chatbot.ChatViewModel", ((Provider) chatViewModelProvider)).put("com.satyasetu.app.ui.village.CreatePostViewModel", ((Provider) createPostViewModelProvider)).put("com.satyasetu.app.ui.feed.FeedViewModel", ((Provider) feedViewModelProvider)).put("com.satyasetu.app.ui.learning.LearningViewModel", ((Provider) learningViewModelProvider)).put("com.satyasetu.app.ui.village.MapViewModel", ((Provider) mapViewModelProvider)).put("com.satyasetu.app.ui.myths.MythViewModel", ((Provider) mythViewModelProvider)).put("com.satyasetu.app.ui.report.ReportViewModel", ((Provider) reportViewModelProvider)).put("com.satyasetu.app.ui.tracker.TrackerViewModel", ((Provider) trackerViewModelProvider)).put("com.satyasetu.app.ui.village.VillageFeedViewModel", ((Provider) villageFeedViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<String, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.satyasetu.app.ui.admin.AdminViewModel 
          return (T) new AdminViewModel(singletonCImpl.reportRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 1: // com.satyasetu.app.ui.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get());

          case 2: // com.satyasetu.app.ui.chatbot.ChatViewModel 
          return (T) new ChatViewModel(singletonCImpl.chatRepositoryProvider.get());

          case 3: // com.satyasetu.app.ui.village.CreatePostViewModel 
          return (T) new CreatePostViewModel(singletonCImpl.postRepositoryProvider.get(), singletonCImpl.villageRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 4: // com.satyasetu.app.ui.feed.FeedViewModel 
          return (T) new FeedViewModel(singletonCImpl.videoRepositoryProvider.get());

          case 5: // com.satyasetu.app.ui.learning.LearningViewModel 
          return (T) new LearningViewModel(singletonCImpl.learningRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 6: // com.satyasetu.app.ui.village.MapViewModel 
          return (T) new MapViewModel(singletonCImpl.villageRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get(), singletonCImpl.provideGeocoderProvider.get());

          case 7: // com.satyasetu.app.ui.myths.MythViewModel 
          return (T) new MythViewModel(singletonCImpl.mythRepositoryProvider.get());

          case 8: // com.satyasetu.app.ui.report.ReportViewModel 
          return (T) new ReportViewModel(singletonCImpl.reportRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 9: // com.satyasetu.app.ui.tracker.TrackerViewModel 
          return (T) new TrackerViewModel(singletonCImpl.trackerRepositoryProvider.get());

          case 10: // com.satyasetu.app.ui.village.VillageFeedViewModel 
          return (T) new VillageFeedViewModel(singletonCImpl.postRepositoryProvider.get(), singletonCImpl.villageRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get(), singletonCImpl.bootstrapRepositoryProvider.get(), singletonCImpl.localeManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends SatyaSetuApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends SatyaSetuApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends SatyaSetuApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<LocaleManager> localeManagerProvider;

    private Provider<FirebaseDatabase> provideFirebaseDatabaseProvider;

    private Provider<FirebaseStorage> provideFirebaseStorageProvider;

    private Provider<ReportRepository> reportRepositoryProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<OpenAIService> provideOpenAIServiceProvider;

    private Provider<ChatRepository> chatRepositoryProvider;

    private Provider<PostRepository> postRepositoryProvider;

    private Provider<VillageRepository> villageRepositoryProvider;

    private Provider<VideoRepository> videoRepositoryProvider;

    private Provider<LearningRepository> learningRepositoryProvider;

    private Provider<Geocoder> provideGeocoderProvider;

    private Provider<MythRepository> mythRepositoryProvider;

    private Provider<TrackerRepository> trackerRepositoryProvider;

    private Provider<BootstrapRepository> bootstrapRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.localeManagerProvider = DoubleCheck.provider(new SwitchingProvider<LocaleManager>(singletonCImpl, 0));
      this.provideFirebaseDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseDatabase>(singletonCImpl, 2));
      this.provideFirebaseStorageProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseStorage>(singletonCImpl, 3));
      this.reportRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ReportRepository>(singletonCImpl, 1));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 5));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 4));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 9));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 8));
      this.provideOpenAIServiceProvider = DoubleCheck.provider(new SwitchingProvider<OpenAIService>(singletonCImpl, 7));
      this.chatRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ChatRepository>(singletonCImpl, 6));
      this.postRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PostRepository>(singletonCImpl, 10));
      this.villageRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<VillageRepository>(singletonCImpl, 11));
      this.videoRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<VideoRepository>(singletonCImpl, 12));
      this.learningRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LearningRepository>(singletonCImpl, 13));
      this.provideGeocoderProvider = DoubleCheck.provider(new SwitchingProvider<Geocoder>(singletonCImpl, 14));
      this.mythRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MythRepository>(singletonCImpl, 15));
      this.trackerRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<TrackerRepository>(singletonCImpl, 16));
      this.bootstrapRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BootstrapRepository>(singletonCImpl, 17));
    }

    @Override
    public void injectSatyaSetuApp(SatyaSetuApp satyaSetuApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.satyasetu.app.util.LocaleManager 
          return (T) new LocaleManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.satyasetu.app.data.repository.ReportRepository 
          return (T) new ReportRepository(singletonCImpl.provideFirebaseDatabaseProvider.get(), singletonCImpl.provideFirebaseStorageProvider.get());

          case 2: // com.google.firebase.database.FirebaseDatabase 
          return (T) AppModule_ProvideFirebaseDatabaseFactory.provideFirebaseDatabase();

          case 3: // com.google.firebase.storage.FirebaseStorage 
          return (T) AppModule_ProvideFirebaseStorageFactory.provideFirebaseStorage();

          case 4: // com.satyasetu.app.data.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirebaseDatabaseProvider.get());

          case 5: // com.google.firebase.auth.FirebaseAuth 
          return (T) AppModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 6: // com.satyasetu.app.data.repository.ChatRepository 
          return (T) new ChatRepository(singletonCImpl.provideOpenAIServiceProvider.get());

          case 7: // com.satyasetu.app.data.remote.OpenAIService 
          return (T) AppModule_ProvideOpenAIServiceFactory.provideOpenAIService(singletonCImpl.provideRetrofitProvider.get());

          case 8: // retrofit2.Retrofit 
          return (T) AppModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 9: // okhttp3.OkHttpClient 
          return (T) AppModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 10: // com.satyasetu.app.data.repository.PostRepository 
          return (T) new PostRepository(singletonCImpl.provideFirebaseDatabaseProvider.get(), singletonCImpl.provideFirebaseStorageProvider.get());

          case 11: // com.satyasetu.app.data.repository.VillageRepository 
          return (T) new VillageRepository(singletonCImpl.provideFirebaseDatabaseProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 12: // com.satyasetu.app.data.repository.VideoRepository 
          return (T) new VideoRepository(singletonCImpl.provideFirebaseDatabaseProvider.get());

          case 13: // com.satyasetu.app.data.repository.LearningRepository 
          return (T) new LearningRepository(singletonCImpl.provideFirebaseDatabaseProvider.get());

          case 14: // android.location.Geocoder 
          return (T) AppModule_ProvideGeocoderFactory.provideGeocoder(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 15: // com.satyasetu.app.data.repository.MythRepository 
          return (T) new MythRepository(singletonCImpl.provideFirebaseDatabaseProvider.get());

          case 16: // com.satyasetu.app.data.repository.TrackerRepository 
          return (T) new TrackerRepository(singletonCImpl.provideFirebaseDatabaseProvider.get());

          case 17: // com.satyasetu.app.data.repository.BootstrapRepository 
          return (T) new BootstrapRepository(singletonCImpl.provideFirebaseDatabaseProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
