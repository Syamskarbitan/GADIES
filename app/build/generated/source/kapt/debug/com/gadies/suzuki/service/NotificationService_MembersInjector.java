package com.gadies.suzuki.service;

import com.gadies.suzuki.data.repository.PidRepository;
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
public final class NotificationService_MembersInjector implements MembersInjector<NotificationService> {
  private final Provider<PidRepository> pidRepositoryProvider;

  public NotificationService_MembersInjector(Provider<PidRepository> pidRepositoryProvider) {
    this.pidRepositoryProvider = pidRepositoryProvider;
  }

  public static MembersInjector<NotificationService> create(
      Provider<PidRepository> pidRepositoryProvider) {
    return new NotificationService_MembersInjector(pidRepositoryProvider);
  }

  @Override
  public void injectMembers(NotificationService instance) {
    injectPidRepository(instance, pidRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.gadies.suzuki.service.NotificationService.pidRepository")
  public static void injectPidRepository(NotificationService instance,
      PidRepository pidRepository) {
    instance.pidRepository = pidRepository;
  }
}
