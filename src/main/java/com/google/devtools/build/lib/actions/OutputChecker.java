// Copyright 2023 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.devtools.build.lib.actions;

/**
 * An interface used to check whether an output should be downloaded or its metadata revalidated
 * when it is stored with a bounded lifetime.
 */
public interface OutputChecker {
  static final OutputChecker TRUST_ALL = (file, metadata) -> true;
  static final OutputChecker TRUST_LOCAL_ONLY = (file, metadata) -> !metadata.isRemote();

  /** Returns whether the given output should be downloaded. */
  default boolean shouldDownloadOutput(ActionInput output, FileArtifactValue metadata) {
    return !shouldTrustMetadata(output, metadata);
  }

  /** Returns whether the given metadata should be trusted. */
  boolean shouldTrustMetadata(ActionInput file, FileArtifactValue metadata);
}
