package com.gadies.suzuki.service;

import android.content.Context;
import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AiService_Factory implements Factory<AiService> {
  private final Provider<Context> contextProvider;

  private final Provider<Gson> gsonProvider;

  public AiService_Factory(Provider<Context> contextProvider, Provider<Gson> gsonProvider) {
    this.contextProvider = contextProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public AiService get() {
    return newInstance(contextProvider.get(), gsonProvider.get());
  }

  public static AiService_Factory create(Provider<Context> contextProvider,
      Provider<Gson> gsonProvider) {
    return new AiService_Factory(contextProvider, gsonProvider);
  }

  public static AiService newInstance(Context context, Gson gson) {
    return new AiService(context, gson);
  }
}
