/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.api;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A profile builder.
 *
 * @author thomas.diesler@jboss.com
 * @since 14-Mar-2014
 */
public interface ProfileBuilder extends AttributableBuilder<ProfileBuilder> {

    ProfileBuilder from(Profile profile);
    
    ProfileBuilder identity(String profileId);

    ProfileBuilder version(String versionId);

    List<String> getParents();
    
    ProfileBuilder addParent(String parentId);

    ProfileBuilder addParents(List<String> parentIds);

    ProfileBuilder setParents(List<String> parentIds);
    
    ProfileBuilder removeParent(String parentId);

    Set<String> getConfigurationKeys();
    
    Map<String, String> getConfiguration(String pid);
    
    ProfileBuilder addConfiguration(String pid, Map<String, String> config);

    ProfileBuilder addConfiguration(String pid, String key, String value);

    ProfileBuilder setConfigurations(Map<String, Map<String, String>> configs);

    ProfileBuilder deleteConfiguration(String pid);

    Set<String> getFileConfigurationKeys();
    
    byte[] getFileConfiguration(String key);
    
    ProfileBuilder addFileConfiguration(String fileName, byte[] data);
    
    ProfileBuilder setFileConfigurations(Map<String, byte[]> configs);

    ProfileBuilder deleteFileConfiguration(String fileName);
    
    ProfileBuilder setBundles(List<String> values);

    ProfileBuilder setFabs(List<String> values);

    ProfileBuilder setFeatures(List<String> values);

    ProfileBuilder setRepositories(List<String> values);

    ProfileBuilder setOverrides(List<String> values);
    
    ProfileBuilder setOptionals(List<String> values);
    
    ProfileBuilder setTags(List<String> values);
    
    ProfileBuilder setOverlay(boolean overlay);
    
    ProfileBuilder setLocked(boolean flag);

    ProfileBuilder setLastModified(String lastModified);
    
    Profile getProfile();

    final class Factory {

        public static ProfileBuilder create() {
            return getProfileBuilders().profileBuilder();
        }

        public static ProfileBuilder create(String profileId) {
            return getProfileBuilders().profileBuilder(profileId);
        }

        public static ProfileBuilder create(String versionId, String profileId) {
            return getProfileBuilders().profileBuilder(versionId, profileId);
        }

        public static ProfileBuilder createFrom(Profile profile) {
            return getProfileBuilders().profileBuilderFrom(profile);
        }

        private static ProfileBuilders getProfileBuilders() {
            return ProfileBuilders.Factory.getProfileBuilders();
        }

        // Hide ctor
        private Factory() {
        }
    }
}
