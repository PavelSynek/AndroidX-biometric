/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.biometric;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.internal.DoNotInstrument;

import androidx.test.filters.MediumTest;

import static androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED;
import static androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE;
import static androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MediumTest
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class BiometricManagerTest {
    @Test
    @Config(minSdk = Build.VERSION_CODES.Q)
    public void testCanAuthenticate_ReturnsSuccess_WhenFrameworkManagerReturnsSuccess() {
        final android.hardware.biometrics.BiometricManager frameworkBiometricManager =
                mock(android.hardware.biometrics.BiometricManager.class);
        when(frameworkBiometricManager.canAuthenticate()).thenReturn(BIOMETRIC_SUCCESS);

        final BiometricManager biometricManager = new BiometricManager(frameworkBiometricManager);
        assertThat(biometricManager.canAuthenticate()).isEqualTo(BIOMETRIC_SUCCESS);
    }

    @Test
    @Config(minSdk = Build.VERSION_CODES.Q)
    public void testCanAuthenticate_ReturnsError_WhenFrameworkManagerReturnsNoneEnrolledError() {
        final android.hardware.biometrics.BiometricManager frameworkBiometricManager =
                mock(android.hardware.biometrics.BiometricManager.class);
        when(frameworkBiometricManager.canAuthenticate()).thenReturn(BIOMETRIC_ERROR_NONE_ENROLLED);

        final BiometricManager biometricManager = new BiometricManager(frameworkBiometricManager);
        assertThat(biometricManager.canAuthenticate()).isEqualTo(BIOMETRIC_ERROR_NONE_ENROLLED);
    }

    @Test
    @Config(minSdk = Build.VERSION_CODES.Q)
    public void testCanAuthenticate_ReturnsError_WhenFrameworkManagerReturnsHardwareError() {
        final android.hardware.biometrics.BiometricManager frameworkBiometricManager =
                mock(android.hardware.biometrics.BiometricManager.class);
        when(frameworkBiometricManager.canAuthenticate()).thenReturn(BIOMETRIC_ERROR_NO_HARDWARE);

        final BiometricManager biometricManager = new BiometricManager(frameworkBiometricManager);
        assertThat(biometricManager.canAuthenticate()).isEqualTo(BIOMETRIC_ERROR_NO_HARDWARE);
    }
}
