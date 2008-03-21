/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.maps.client.event;

import com.google.gwt.maps.client.overlay.Marker;

/**
 * DragListener defines the interface for an object that listens to drag events
 * on a {@link  com.google.gwt.maps.client.overlay.Marker Marker}.
 * 
 * @see Marker#addMarkerDragListener(MarkerDragListener)
 */
public interface MarkerDragListener {

  /**
   * Called in response to a "drag" event. This event is fired continuously
   * while the user drags the marker.
   */
  void onDrag(Marker sender);

  /**
   * Called in response to a "dragend" event. This event is fired when the user
   * stops dragging the marker.
   */
  void onDragEnd(Marker sender);

  /**
   * Called in response to a "dragstart" event. Fired when the user begins
   * dragging the marker.
   */
  void onDragStart(Marker sender);

}