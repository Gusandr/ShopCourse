package ru.gusandr.shopcourse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.gusandr.domain.usecase.OpenSocialAuthUseCase
import ru.gusandr.domain.usecase.ValidateEmailUseCase
import ru.gusandr.domain.usecase.ValidateLoginPasswordUseCase
import ru.gusandr.domain.usecase.ValidateRegistrationPasswordUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidateRegistrationPasswordUseCase {
        return ValidateRegistrationPasswordUseCase()
    }

    @Provides
    fun provideOpenSocialAuthUseCase(): OpenSocialAuthUseCase {
        return OpenSocialAuthUseCase()
    }

    @Provides
    fun provideValidateLoginPasswordUseCase(): ValidateLoginPasswordUseCase {
        return ValidateLoginPasswordUseCase()
    }
}