/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.jbpm.console.ng.es.model;

import org.jbpm.console.ng.ga.service.ItemKey;

public class RequestKey implements ItemKey {
    private Long id;
    public RequestKey() {
    }

    public RequestKey(Long id) {
      this.id = id;
    }

    public Long getId() {
      return id;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final RequestKey other = (RequestKey) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "RequestKey{" + "RequestId=" + id + '}';
    }
}
