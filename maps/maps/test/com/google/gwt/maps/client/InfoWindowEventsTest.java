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
package com.google.gwt.maps.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.maps.client.event.InfoWindowCloseClickHandler;
import com.google.gwt.maps.client.event.InfoWindowMaximizeClickHandler;
import com.google.gwt.maps.client.event.InfoWindowMaximizeEndHandler;
import com.google.gwt.maps.client.event.InfoWindowRestoreClickHandler;
import com.google.gwt.maps.client.event.InfoWindowRestoreEndHandler;
import com.google.gwt.maps.client.event.MapInfoWindowOpenHandler;
import com.google.gwt.maps.client.event.InfoWindowCloseClickHandler.InfoWindowCloseClickEvent;
import com.google.gwt.maps.client.event.InfoWindowRestoreEndHandler.InfoWindowRestoreEndEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Tests for the InfoWindow events.
 * 
 * Design Note(zundel): These events are, in theory, asynchronous, but as I
 * wrote these test cases, it seems that they are called synchronously in many
 * cases. Nevertheless, I'm leaving the asynchronous test infrastructure in case
 * the underlying implementation changes.
 * 
 * Most events have a test that is triggered by the GEvent.trigger() mechanism
 * (testXXXTrigger()) as well as a test that is triggered by API calls
 * (testXXXEvent()). Some of the events depend on user interaction and cannot be
 * triggered by the Maps API calls.
 */
public class InfoWindowEventsTest extends GWTTestCase {
  // length of time to wait for asynchronous test to complete.
  static final int ASYNC_DELAY_MSEC = 5000; // 5 seconds

  @Override
  public String getModuleName() {
    return "com.google.gwt.maps.GoogleMapsTest";
  }


  /**
   * Runs before every test method.
   */
   public void gwtSetUp() {
     TestUtilities.cleanDom();
   }

  /*
   * Design note: the "closeclick" event doesn't fire through the API. It is for
   * detecting clicks on the close button.
   */
  
  public void testInfoWindowCloseClickTrigger() {
    final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580), 13);
    map.setSize("300px", "300px");
    RootPanel.get().add(map);

    final InfoWindow info = map.getInfoWindow();
    info.addInfoWindowCloseClickHandler(new InfoWindowCloseClickHandler() {

      public void onCloseClick(InfoWindowCloseClickEvent event) {
        assertEquals(event.getSender(), info);
        finishTest();
      }

    });
    info.open(map.getCenter(), new InfoWindowContent("Hello Maps!"));
    InfoWindowCloseClickEvent e = new InfoWindowCloseClickEvent(info);
    this.delayTestFinish(ASYNC_DELAY_MSEC);
    info.trigger(e);
  }

  public void testInfoWindowMaximizeClickEvent() {
    final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580), 13);
    map.setSize("300px", "300px");

    RootPanel.get().add(map);

    final InfoWindow info = map.getInfoWindow();
    InfoWindowContent content = new InfoWindowContent("HelloMaps!");
    content.setMaxContent("Hello Again Maps!");
    content.setMaxTitle("Hello Maps Maximized");
    // Handler to maximize the info window as soon as it opens.
    map.addInfoWindowOpenHandler(new MapInfoWindowOpenHandler() {

      public void onInfoWindowOpen(MapInfoWindowOpenEvent event) {
        // System.out.println("Maximizing info window");
        info.maximize();
      }

    });
    info.addInfoWindowMaximizeClickHandler(new InfoWindowMaximizeClickHandler() {

      public void onMaximizeClick(InfoWindowMaximizeClickEvent event) {
        assertEquals(event.getSender(), info);
        finishTest();
      }

    });

    delayTestFinish(ASYNC_DELAY_MSEC);
    info.open(map.getCenter(), content);
  }

  // TODO(zundel): JavaScript failure inside maps API. Filed bug w/ maps team.

  // public void testInfoWindowMaximizeClickTrigger() {
  // final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580),
  // 13);
  // map.setSize("300px", "300px");
  //
  // RootPanel.get().add(map);
  //
  // final InfoWindow info = map.getInfoWindow();
  // InfoWindowContent content = new InfoWindowContent("HelloMaps!");
  // content.setMaxContent("Hello Again Maps!");
  // content.setMaxTitle("Hello Maps Maximized");
  // info.open(map.getCenter(), content);
  // info.addInfoWindowMaximizeClickHandler(new InfoWindowMaximizeClickHandler()
  // {
  //
  // public void onMaximizeClick(InfoWindowMaximizeClickEvent event) {
  // assertEquals(event.getSender(), info);
  // finishTest();
  // }
  //
  // });
  // InfoWindowMaximizeClickEvent e = new InfoWindowMaximizeClickEvent(info);
  // delayTestFinish(ASYNC_DELAY_MSEC);
  // info.trigger(e);
  // }

  public void testInfoWindowMaximizeEndEvent() {
    final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580), 13);
    map.setSize("300px", "300px");
    RootPanel.get().add(map);

    final InfoWindow info = map.getInfoWindow();
    InfoWindowContent content = new InfoWindowContent("HelloMaps!");
    content.setMaxContent("Hello Again Maps!");
    content.setMaxTitle("Hello Maps Maximized");
    // Handler to maximize the info window as soon as it opens.
    map.addInfoWindowOpenHandler(new MapInfoWindowOpenHandler() {

      public void onInfoWindowOpen(MapInfoWindowOpenEvent event) {
        // System.out.println("Maximizing info window");
        info.maximize();
      }

    });
    info.addInfoWindowMaximizeEndHandler(new InfoWindowMaximizeEndHandler() {

      public void onMaximizeEnd(InfoWindowMaximizeEndEvent event) {
        assertEquals(event.getSender(), info);
        finishTest();
      }

    });
    delayTestFinish(ASYNC_DELAY_MSEC);
    info.open(map.getCenter(), content);
  }

  // TODO(zundel): Javascript failure inside maps API. Filed bug w/ maps team.

  // public void testInfoWindowMaximizeEndTrigger() {
  // final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580),
  // 13);
  // map.setSize("300px", "300px");
  // RootPanel.get().add(map);
  //
  // final InfoWindow info = map.getInfoWindow();
  // InfoWindowContent content = new InfoWindowContent("HelloMaps!");
  // content.setMaxContent("Hello Again Maps!");
  // content.setMaxTitle("Hello Maps Maximized");
  // info.open(map.getCenter(), content);
  // info.addInfoWindowMaximizeEndHandler(new InfoWindowMaximizeEndHandler() {
  //
  // public void onMaximizeEnd(InfoWindowMaximizeEndEvent event) {
  // assertEquals(event.getSender(), info);
  // finishTest();
  // }
  //
  // });
  // info.maximize();
  // InfoWindowMaximizeEndEvent e = new InfoWindowMaximizeEndEvent(info);
  // delayTestFinish(ASYNC_DELAY_MSEC);
  // info.trigger(e);
  // }

  public void testInfoWindowRestoreClickEvent() {
    final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580), 13);
    map.setSize("300px", "300px");
    RootPanel.get().add(map);

    final InfoWindow info = map.getInfoWindow();
    InfoWindowContent content = new InfoWindowContent("HelloMaps!");
    content.setMaxContent("Hello Again Maps!");
    content.setMaxTitle("Hello Maps Maximized");
    // Handler to maximize the info window as soon as it opens.
    map.addInfoWindowOpenHandler(new MapInfoWindowOpenHandler() {

      public void onInfoWindowOpen(MapInfoWindowOpenEvent event) {
        info.maximize();
        info.restore();
      }

    });
    info.addInfoWindowRestoreClickHandler(new InfoWindowRestoreClickHandler() {

      public void onRestoreClick(InfoWindowRestoreClickEvent event) {
        assertEquals(event.getSender(), info);
        finishTest();
      }

    });
    delayTestFinish(ASYNC_DELAY_MSEC);
    info.open(map.getCenter(), content);
  }

  // TODO(zundel): Javascript failure inside maps API. Filed bug w/ maps team.

  // public void testInfoWindowRestoreClickTrigger() {
  // final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580),
  // 13);
  // map.setSize("300px", "300px");
  // RootPanel.get().add(map);
  //
  // final InfoWindow info = map.getInfoWindow();
  // InfoWindowContent content = new InfoWindowContent("HelloMaps!");
  // content.setMaxContent("Hello Again Maps!");
  // content.setMaxTitle("Hello Maps Maximized");
  // info.open(map.getCenter(), content);
  // info.addInfoWindowRestoreClickHandler(new InfoWindowRestoreClickHandler() {
  //
  // public void onRestoreClick(InfoWindowRestoreClickEvent event) {
  // assertEquals(event.getSender(), info);
  // finishTest();
  // }
  //
  // });
  // info.maximize();
  // InfoWindowRestoreClickEvent e = new InfoWindowRestoreClickEvent(info);
  // delayTestFinish(ASYNC_DELAY_MSEC);
  // info.trigger(e);
  // }

  public void testInfoWindowRestoreEndEvent() {
    final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580), 13);
    map.setSize("300px", "300px");
    RootPanel.get().add(map);

    final InfoWindow info = map.getInfoWindow();
    InfoWindowContent content = new InfoWindowContent("HelloMaps!");
    content.setMaxContent("Hello Again Maps!");
    content.setMaxTitle("Hello Maps Maximized");
    // Handler to maximize the info window as soon as it opens.
    map.addInfoWindowOpenHandler(new MapInfoWindowOpenHandler() {

      public void onInfoWindowOpen(MapInfoWindowOpenEvent event) {
        info.maximize();
        info.restore();
      }

    });
    info.addInfoWindowRestoreEndHandler(new InfoWindowRestoreEndHandler() {

      public void onRestoreEnd(InfoWindowRestoreEndEvent event) {
        assertEquals(event.getSender(), info);
        finishTest();
      }

    });
    delayTestFinish(ASYNC_DELAY_MSEC);
    info.open(map.getCenter(), content);
  }

  public void testInfoWindowRestoreEndTrigger() {
    final MapWidget map = new MapWidget(new LatLng(33.7814790, -84.3880580), 13);
    map.setSize("300px", "300px");
    RootPanel.get().add(map);

    final InfoWindow info = map.getInfoWindow();
    InfoWindowContent content = new InfoWindowContent("HelloMaps!");
    content.setMaxContent("Hello Again Maps!");
    content.setMaxTitle("Hello Maps Maximized");
    info.open(map.getCenter(), content);
    info.addInfoWindowRestoreEndHandler(new InfoWindowRestoreEndHandler() {

      public void onRestoreEnd(InfoWindowRestoreEndEvent event) {
        assertEquals(event.getSender(), info);
        finishTest();
      }

    });
    info.maximize();
    InfoWindowRestoreEndEvent e = new InfoWindowRestoreEndEvent(info);
    delayTestFinish(ASYNC_DELAY_MSEC);
    info.trigger(e);
  }
}
