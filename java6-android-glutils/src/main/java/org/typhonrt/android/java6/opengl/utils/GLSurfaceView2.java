/**
 * Copyright 2015 Michael Leahy / TyphonRT, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.typhonrt.android.java6.opengl.utils;

/**
 * ORIGINAL SOURCE -
 * Copyright (C) 2008 The Android Open Source Project
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
import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.typhonrt.commons.java6.opengl.utils.IGLVersion;
import org.typhonrt.commons.java6.opengl.utils.XeGLUnknown;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * An implementation of SurfaceView that uses the dedicated surface for displaying OpenGL rendering.
 * <p>
 * A GLSurfaceView2 provides the following features:
 * <p>
 * <ul>
 * <li>Manages a surface, which is a special piece of memory that can be composited into the Android view system.
 * <li>Manages an EGL display, which enables OpenGL to render into a surface.
 * <li>Accepts a user-provided Renderer object that does the actual rendering.
 * <li>Renders on a dedicated thread to decouple rendering performance from the UI thread.
 * <li>Supports both on-demand and continuous rendering.
 * </ul>
 * <p/>
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For more information about how to use OpenGL, read the
 * <a href="{@docRoot}guide/topics/graphics/opengl.html">OpenGL</a> developer guide.</p>
 * </div>
 * <p/>
 * <h3>Using GLSurfaceView2</h3>
 * <p/>
 * Typically you use GLSurfaceView2 by subclassing it and overriding one or more of the
 * View system input event methods. If your application does not need to override event
 * methods then GLSurfaceView2 can be used as-is. For the most part
 * GLSurfaceView2 behavior is customized by calling "set" methods rather than by subclassing.
 * For example, unlike a regular View, drawing is delegated to a separate Renderer object which
 * is registered with the GLSurfaceView2
 * using the {@link #setRenderer(Renderer)} call.
 * <p/>
 * <h3>Initializing GLSurfaceView2</h3>
 * All you have to do to initialize a GLSurfaceView2 is call {@link #setRenderer(Renderer)}.
 * However, if desired, you can modify the default behavior of GLSurfaceView2 by calling one or
 * more of these methods before calling setRenderer:
 * <ul>
 * <li>{@link #setDebugFlags(int)}
 * <li>{@link #setEGLConfigChooser(boolean)}
 * <li>{@link #setEGLConfigChooser(EGLConfigChooser)}
 * <li>{@link #setEGLConfigChooser(int, int, int, int, int, int)}
 * </ul>
 * <p/>
 * <h4>Specifying the android.view.Surface</h4>
 * By default GLSurfaceView2 will create a PixelFormat.RGB_888 format surface. If a translucent
 * surface is required, call getHolder().setFormat(PixelFormat.TRANSLUCENT).
 * The exact format of a TRANSLUCENT surface is device dependent, but it will be
 * a 32-bit-per-pixel surface with 8 bits per component.
 * <p/>
 * <h4>Choosing an EGL Configuration</h4>
 * A given Android device may support multiple EGLConfig rendering configurations.
 * The available configurations may differ in how may channels of data are present, as
 * well as how many bits are allocated to each channel. Therefore, the first thing
 * GLSurfaceView2 has to do when starting to render is choose what EGLConfig to use.
 * <p/>
 * By default GLSurfaceView2 chooses a EGLConfig that has an RGB_888 pixel format,
 * with at least a 16-bit depth buffer and no stencil.
 * <p/>
 * If you would prefer a different EGLConfig
 * you can override the default behavior by calling one of the
 * setEGLConfigChooser methods.
 * <p/>
 * <h4>Setting a Renderer</h4>
 * Finally, you must call {@link #setRenderer} to register a {@link Renderer}.
 * The renderer is
 * responsible for doing the actual OpenGL rendering.
 * <p/>
 * <h3>Rendering Mode</h3>
 * Once the renderer is set, you can control whether the renderer draws
 * continuously or on-demand by calling
 * {@link #setRenderMode}. The default is continuous rendering.
 * <p/>
 * <h3>Activity Life-cycle</h3>
 * A GLSurfaceView2 must be notified when the activity is paused and resumed. GLSurfaceView2 clients
 * are required to call {@link #onPause()} when the activity pauses and
 * {@link #onResume()} when the activity resumes. These calls allow GLSurfaceView2 to
 * pause and resume the rendering thread, and also allow GLSurfaceView2 to release and recreate
 * the OpenGL display.
 * <p/>
 * <h3>Handling events</h3>
 * <p/>
 * To handle an event you will typically subclass GLSurfaceView2 and override the
 * appropriate method, just as you would with any other View. However, when handling
 * the event, you may need to communicate with the Renderer object
 * that's running in the rendering thread. You can do this using any
 * standard Java cross-thread communication mechanism. In addition,
 * one relatively easy way to communicate with your renderer is
 * to call
 * {@link #queueEvent(Runnable)}. For example:
 * <pre class="prettyprint">
 * class MyGLSurfaceView extends GLSurfaceView2 {
 * <p/>
 * private MyRenderer mMyRenderer;
 * <p/>
 * public void start() {
 * mMyRenderer = ...;
 * setRenderer(mMyRenderer);
 * }
 * <p/>
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
 * queueEvent(new Runnable() {
 * // This method will be called on the rendering
 * // thread:
 * public void run() {
 * mMyRenderer.handleDpadCenter();
 * }});
 * return true;
 * }
 * return super.onKeyDown(keyCode, event);
 * }
 * }
 * </pre>
 */
public class GLSurfaceView2 extends SurfaceView implements SurfaceHolder.Callback
{
   private final static String   s_TAG = "GLSurfaceView2";
   private final static boolean  s_LOG_ATTACH_DETACH = false;
   private final static boolean  s_LOG_THREADS = false;
   private final static boolean  s_LOG_PAUSE_RESUME = false;
   private final static boolean  s_LOG_SURFACE = false;
   private final static boolean  s_LOG_RENDERER = false;
   private final static boolean  s_LOG_RENDERER_DRAW_FRAME = false;
   private final static boolean  s_LOG_EGL = false;

   private static final int      s_DEFAULT_SURFACE_ATTRIBS[] = { EGL14.EGL_NONE };

   /**
    * The renderer only renders
    * when the surface is created, or when {@link #requestRender} is called.
    *
    * @see #getRenderMode()
    * @see #setRenderMode(int)
    * @see #requestRender()
    */
   public final static int       s_RENDERMODE_WHEN_DIRTY = 0;
   /**
    * The renderer is called
    * continuously to re-render the scene.
    *
    * @see #getRenderMode()
    * @see #setRenderMode(int)
    */
   public final static int       s_RENDERMODE_CONTINUOUSLY = 1;

   private static final GLThreadManager         s_GLThreadManager = new GLThreadManager();

   private final WeakReference<GLSurfaceView2>  thisWeakRef = new WeakReference<GLSurfaceView2>(this);
   private GLThread                             glThread;
   private Renderer                             renderer;
   private boolean                              detached;
   private EGLConfigChooser                     eglConfigChooser;
   private EGLContextFactory                    eglContextFactory;
   private EGLWindowSurfaceFactory              eglWindowSurfaceFactory;
   private int                                  debugFlags;

   private IGLVersion                           eglContextGLESVersion;
   private IGLVersion                           actualEGLContextGLESVersion = XeGLUnknown.GL_UNKNOWN;

   private boolean                              preserveEGLContextOnPause;

   /**
    * Standard View constructor. In order to render something, you
    * must call {@link #setRenderer} to register a renderer.
    */
   public GLSurfaceView2(Context context)
   {
      super(context);
      init();
   }

   /**
    * Standard View constructor. In order to render something, you
    * must call {@link #setRenderer} to register a renderer.
    */
   public GLSurfaceView2(Context context, AttributeSet attrs)
   {
      super(context, attrs);
      init();
   }

   public GLSurfaceView2(Context context, AttributeSet attrs, int defStyleAttr)
   {
      super(context, attrs, defStyleAttr);
      init();
   }

   public GLSurfaceView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
   {
      super(context, attrs, defStyleAttr, defStyleRes);
      init();
   }

   @Override
   protected void finalize() throws Throwable
   {
      try
      {
         if (glThread != null)
         {
            // GLThread may still be running if this view was never
            // attached to a window.
            glThread.requestExitAndWait();
         }
      }
      finally
      {
         super.finalize();
      }
   }

   private void init()
   {
      // Install a SurfaceHolder.Callback so we get notified when the
      // underlying surface is created and destroyed
      SurfaceHolder holder = getHolder();
      holder.addCallback(this);
   }

   /**
    * Set the debug flags to a new value. The value is
    * constructed by OR-together zero or more
    * of the DEBUG_CHECK_* constants. The debug flags take effect
    * whenever a surface is created. The default value is zero.
    *
    * @param debugFlags the new debug flags
    */
   public void setDebugFlags(int debugFlags)
   {
      this.debugFlags = debugFlags;
   }

   public IGLVersion getContextGLESVersion()
   {
      return actualEGLContextGLESVersion;
   }

   /**
    * Get the current value of the debug flags.
    *
    * @return the current value of the debug flags.
    */
   public int getDebugFlags()
   {
      return debugFlags;
   }

   /**
    * Control whether the EGL context is preserved when the GLSurfaceView2 is paused and
    * resumed.
    * <p/>
    * If set to true, then the EGL context may be preserved when the GLSurfaceView2 is paused.
    * Whether the EGL context is actually preserved or not depends upon whether the
    * Android device that the program is running on can support an arbitrary number of EGL
    * contexts or not. Devices that can only support a limited number of EGL contexts must
    * release the  EGL context in order to allow multiple applications to share the GPU.
    * <p/>
    * If set to false, the EGL context will be released when the GLSurfaceView2 is paused,
    * and recreated when the GLSurfaceView2 is resumed.
    * <p/>
    * <p/>
    * The default is false.
    *
    * @param preserveOnPause preserve the EGL context when paused
    */
   public void setPreserveEGLContextOnPause(boolean preserveOnPause)
   {
      preserveEGLContextOnPause = preserveOnPause;
   }

   /**
    * @return true if the EGL context will be preserved when paused
    */
   public boolean getPreserveEGLContextOnPause()
   {
      return preserveEGLContextOnPause;
   }

   /**
    * Set the renderer associated with this view. Also starts the thread that
    * will call the renderer, which in turn causes the rendering to start.
    * <p>This method should be called once and only once in the life-cycle of
    * a GLSurfaceView2.
    * <p>The following GLSurfaceView2 methods can only be called <em>before</em>
    * setRenderer is called:
    * <ul>
    * <li>{@link #setEGLConfigChooser(boolean)}
    * <li>{@link #setEGLConfigChooser(EGLConfigChooser)}
    * <li>{@link #setEGLConfigChooser(int, int, int, int, int, int)}
    * </ul>
    * <p/>
    * The following GLSurfaceView2 methods can only be called <em>after</em>
    * setRenderer is called:
    * <ul>
    * <li>{@link #getRenderMode()}
    * <li>{@link #onPause()}
    * <li>{@link #onResume()}
    * <li>{@link #queueEvent(Runnable)}
    * <li>{@link #requestRender()}
    * <li>{@link #setRenderMode(int)}
    * </ul>
    *
    * @param renderer the renderer to use to perform OpenGL drawing.
    */
   public void setRenderer(Renderer renderer)
   {
      checkRenderThreadState();

      if (eglConfigChooser == null)
      {
         eglConfigChooser = new SimpleEGLConfigChooser(true);
      }

      if (eglContextFactory == null)
      {
         eglContextFactory = new DefaultContextFactory();
      }

      if (eglWindowSurfaceFactory == null)
      {
         eglWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
      }

      this.renderer = renderer;
      glThread = new GLThread(thisWeakRef);
      glThread.start();
   }

   /**
    * Install a custom EGLContextFactory.
    * <p>If this method is
    * called, it must be called before {@link #setRenderer(Renderer)}
    * is called.
    * <p/>
    * If this method is not called, then by default
    * a context will be created with no shared context and
    * with a null attribute list.
    */
   public void setEGLContextFactory(EGLContextFactory factory)
   {
      checkRenderThreadState();
      eglContextFactory = factory;
   }

   /**
    * Install a custom EGLWindowSurfaceFactory.
    * <p>If this method is
    * called, it must be called before {@link #setRenderer(Renderer)}
    * is called.
    * <p/>
    * If this method is not called, then by default
    * a window surface will be created with a null attribute list.
    */
   public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory)
   {
      checkRenderThreadState();
      eglWindowSurfaceFactory = factory;
   }

   /**
    * Install a custom EGLConfigChooser.
    * <p>If this method is
    * called, it must be called before {@link #setRenderer(Renderer)}
    * is called.
    * <p/>
    * If no setEGLConfigChooser method is called, then by default the
    * view will choose an EGLConfig that is compatible with the current
    * android.view.Surface, with a depth buffer depth of
    * at least 16 bits.
    *
    * @param configChooser
    */
   public void setEGLConfigChooser(EGLConfigChooser configChooser)
   {
      checkRenderThreadState();
      eglConfigChooser = configChooser;
   }

   /**
    * Install a config chooser which will choose a config
    * as close to 16-bit RGB as possible, with or without an optional depth
    * buffer as close to 16-bits as possible.
    * <p>If this method is
    * called, it must be called before {@link #setRenderer(Renderer)}
    * is called.
    * <p/>
    * If no setEGLConfigChooser method is called, then by default the
    * view will choose an RGB_888 surface with a depth buffer depth of
    * at least 16 bits.
    *
    * @param needDepth
    */
   public void setEGLConfigChooser(boolean needDepth)
   {
      setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
   }

   /**
    * Install a config chooser which will choose a config
    * with at least the specified depthSize and stencilSize,
    * and exactly the specified redSize, greenSize, blueSize and alphaSize.
    * <p>If this method is
    * called, it must be called before {@link #setRenderer(Renderer)}
    * is called.
    * <p/>
    * If no setEGLConfigChooser method is called, then by default the
    * view will choose an RGB_888 surface with a depth buffer depth of
    * at least 16 bits.
    */
   public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize,
    int stencilSize)
   {
      setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize,
       blueSize, alphaSize, depthSize, stencilSize));
   }

   public void setPresentationTime(long timestamp)
   {
      glThread.setPresentationTime(timestamp);
      setPresentationTime(timestamp);
   }

   /**
    * Inform the default EGLContextFactory and default EGLConfigChooser
    * which EGLContext client version to pick.
    * <p>Use this method to create an OpenGL ES 2.0-compatible context.
    * Example:
    * <pre class="prettyprint">
    * public MyView(Context context) {
    * super(context);
    * setEGLContextClientVersion(2); // Pick an OpenGL ES 2.0 context.
    * setRenderer(new MyRenderer());
    * }
    * </pre>
    * <p>Note: Activities which require OpenGL ES 2.0 should indicate this by
    * setting @lt;uses-feature android:glEsVersion="0x00020000" /> in the activity's
    * AndroidManifest.xml file.
    * <p>If this method is called, it must be called before {@link #setRenderer(Renderer)}
    * is called.
    * <p>This method only affects the behavior of the default EGLContexFactory and the
    * default EGLConfigChooser. If
    * {@link #setEGLContextFactory(EGLContextFactory)} has been called, then the supplied
    * EGLContextFactory is responsible for creating an OpenGL ES 2.0-compatible context.
    * If
    * {@link #setEGLConfigChooser(EGLConfigChooser)} has been called, then the supplied
    * EGLConfigChooser is responsible for choosing an OpenGL ES 2.0-compatible config.
    *
    * @param version The EGLContext client version to choose. Use 2 for OpenGL ES 2.0
    */
   public void setEGLContextGLESVersion(IGLVersion version)
   {
      checkRenderThreadState();
      eglContextGLESVersion = version;
   }

   /**
    * Set the rendering mode. When renderMode is
    * s_RENDERMODE_CONTINUOUSLY, the renderer is called
    * repeatedly to re-render the scene. When renderMode
    * is s_RENDERMODE_WHEN_DIRTY, the renderer only rendered when the surface
    * is created, or when {@link #requestRender} is called. Defaults to s_RENDERMODE_CONTINUOUSLY.
    * <p/>
    * Using s_RENDERMODE_WHEN_DIRTY can improve battery life and overall system performance
    * by allowing the GPU and CPU to idle when the view does not need to be updated.
    * <p/>
    * This method can only be called after {@link #setRenderer(Renderer)}
    *
    * @param renderMode one of the RENDERMODE_X constants
    * @see #s_RENDERMODE_CONTINUOUSLY
    * @see #s_RENDERMODE_WHEN_DIRTY
    */
   public void setRenderMode(int renderMode)
   {
      glThread.setRenderMode(renderMode);
   }

   /**
    * Get the current rendering mode. May be called
    * from any thread. Must not be called before a renderer has been set.
    *
    * @return the current rendering mode.
    * @see #s_RENDERMODE_CONTINUOUSLY
    * @see #s_RENDERMODE_WHEN_DIRTY
    */
   public int getRenderMode()
   {
      return glThread.getRenderMode();
   }

   /**
    * Request that the renderer render a frame.
    * This method is typically used when the render mode has been set to
    * {@link #s_RENDERMODE_WHEN_DIRTY}, so that frames are only rendered on demand.
    * May be called
    * from any thread. Must not be called before a renderer has been set.
    */
   public void requestRender()
   {
      glThread.requestRender();
   }

   /**
    * This method is part of the SurfaceHolder.Callback interface, and is
    * not normally called or subclassed by clients of GLSurfaceView2.
    */
   public void surfaceCreated(SurfaceHolder holder)
   {
      glThread.surfaceCreated();
   }

   /**
    * This method is part of the SurfaceHolder.Callback interface, and is
    * not normally called or subclassed by clients of GLSurfaceView2.
    */
   public void surfaceDestroyed(SurfaceHolder holder)
   {
      // Surface will be destroyed when we return
      glThread.surfaceDestroyed();
   }

   /**
    * This method is part of the SurfaceHolder.Callback interface, and is
    * not normally called or subclassed by clients of GLSurfaceView2.
    */
   public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
   {
      glThread.onWindowResize(w, h);
   }

   /**
    * Inform the view that the activity is paused. The owner of this view must
    * call this method when the activity is paused. Calling this method will
    * pause the rendering thread.
    * Must not be called before a renderer has been set.
    */
   public void onPause()
   {
      glThread.onPause();
   }

   /**
    * Inform the view that the activity is resumed. The owner of this view must
    * call this method when the activity is resumed. Calling this method will
    * recreate the OpenGL display and resume the rendering
    * thread.
    * Must not be called before a renderer has been set.
    */
   public void onResume()
   {
      glThread.onResume();
   }

   /**
    * Queue a runnable to be run on the GL rendering thread. This can be used
    * to communicate with the Renderer on the rendering thread.
    * Must not be called before a renderer has been set.
    *
    * @param r the runnable to be run on the GL rendering thread.
    */
   public void queueEvent(Runnable r)
   {
      glThread.queueEvent(r);
   }

   /**
    * This method is used as part of the View class and is not normally
    * called or subclassed by clients of GLSurfaceView2.
    */
   @Override
   protected void onAttachedToWindow()
   {
      super.onAttachedToWindow();

      if (s_LOG_ATTACH_DETACH)
      {
         Log.d(s_TAG, "onAttachedToWindow reattach =" + detached);
      }

      if (detached && (renderer != null))
      {
         int renderMode = s_RENDERMODE_CONTINUOUSLY;

         if (glThread != null)
         {
            renderMode = glThread.getRenderMode();
         }

         glThread = new GLThread(thisWeakRef);

         if (renderMode != s_RENDERMODE_CONTINUOUSLY)
         {
            glThread.setRenderMode(renderMode);
         }

         glThread.start();
      }

      detached = false;
   }

   @Override
   protected void onDetachedFromWindow()
   {
      if (s_LOG_ATTACH_DETACH)
      {
         Log.d(s_TAG, "onDetachedFromWindow");
      }

      if (glThread != null)
      {
         glThread.requestExitAndWait();
      }

      detached = true;

      super.onDetachedFromWindow();
   }

   // ----------------------------------------------------------------------

   /**
    * A generic renderer interface.
    * <p>
    * The renderer is responsible for making OpenGL calls to render a frame.
    * <p>
    * GLSurfaceView2 clients typically create their own classes that implement
    * this interface, and then call {@link GLSurfaceView2#setRenderer} to
    * register the renderer with the GLSurfaceView2.
    * <p>
    * <p/>
    * <div class="special reference">
    * <h3>Developer Guides</h3>
    * <p>For more information about how to use OpenGL, read the
    * <a href="{@docRoot}guide/topics/graphics/opengl.html">OpenGL</a> developer guide.</p>
    * </div>
    * <p/>
    * <h3>Threading</h3>
    * The renderer will be called on a separate thread, so that rendering
    * performance is decoupled from the UI thread. Clients typically need to
    * communicate with the renderer from the UI thread, because that's where
    * input events are received. Clients can communicate using any of the
    * standard Java techniques for cross-thread communication, or they can
    * use the {@link GLSurfaceView2#queueEvent(Runnable)} convenience method.
    * <p/>
    * <h3>EGL Context Lost</h3>
    * There are situations where the EGL rendering context will be lost. This
    * typically happens when device wakes up after going to sleep. When
    * the EGL context is lost, all OpenGL resources (such as textures) that are
    * associated with that context will be automatically deleted. In order to
    * keep rendering correctly, a renderer must recreate any lost resources
    * that it still needs. The {@link #onGLSurfaceCreated()} method
    * is a convenient place to do this.
    *
    * @see #setRenderer(Renderer)
    */
   public interface Renderer
   {
      /**
       * Called when the context is created or recreated.
       * <p/>
       * Called when the rendering thread
       * starts and whenever the EGL context is created.
       * <p/>
       * Since this method is called at the beginning of rendering, as well as
       * every time the EGL context is lost, this method is a convenient place to put
       * code to check resources that need to be created before rendering
       * starts, and that need to be recreated when the EGL context is lost.
       * <p/>
       * Note that when the EGL context is lost, all OpenGL resources associated
       * with that context will be automatically deleted. You do not need to call
       * the corresponding "glDelete" methods such as glDeleteTextures to
       * manually dispose these lost resources.
       * <p/>
       */
      void onGLContextCreated();

      /**
       * Called when the surface is created or recreated.
       * <p/>
       * Called when the rendering thread
       * starts and whenever the EGL context is lost. The EGL context will typically
       * be lost when the Android device awakes after going to sleep.
       * <p/>
       * Since this method is called at the beginning of rendering, as well as
       * every time the EGL context is lost, this method is a convenient place to put
       * code to create resources that need to be created when the rendering
       * starts, and that need to be recreated when the EGL context is lost.
       * Textures are an example of a resource that you might want to create
       * here.
       * <p/>
       * Note that when the EGL context is lost, all OpenGL resources associated
       * with that context will be automatically deleted. You do not need to call
       * the corresponding "glDelete" methods such as glDeleteTextures to
       * manually dispose these lost resources.
       * <p/>
       */
      void onGLSurfaceCreated();

      /**
       * Called when the surface changed size.
       * <p/>
       * Called after the surface is created and whenever
       * the OpenGL ES surface size changes.
       * <p/>
       * Typically you will set your viewport here. If your camera
       * is fixed then you could also set your projection matrix here:
       * <pre class="prettyprint">
       * void onGLSurfaceChanged(int width, int height) {
       * gl.glViewport(0, 0, width, height);
       * // for a fixed camera, set the projection too
       * float ratio = (float) width / height;
       * gl.glMatrixMode(GL10.GL_PROJECTION);
       * gl.glLoadIdentity();
       * gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
       * }
       * </pre>
       *
       * @param width
       * @param height
       */
      void onGLSurfaceChanged(int width, int height);

      /**
       * Called to draw the current frame.
       * <p/>
       * This method is responsible for drawing the current frame.
       * <p/>
       * The implementation of this method typically looks like this:
       * <pre class="prettyprint">
       * void onGLDrawFrame(GL10 gl) {
       * gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
       * //... other gl calls to render the scene ...
       * }
       * </pre>
       */
      void onGLDrawFrame();
   }

   /**
    * An interface for customizing the eglCreateContext and eglDestroyContext calls.
    * <p/>
    * This interface must be implemented by clients wishing to call
    * {@link GLSurfaceView2#setEGLContextFactory(EGLContextFactory)}
    */

   public interface EGLContextFactory
   {
      EGLContext createContext(EGLDisplay display, EGLConfig eglConfig);

      void destroyContext(EGLDisplay display, EGLContext context);
   }

   private class DefaultContextFactory implements EGLContextFactory
   {
      public EGLContext createContext(EGLDisplay display, EGLConfig config)
      {
         // If minor version is specified then use EGL_CONTEXT_MAJOR_VERSION_KHR / EGL_CONTEXT_MINOR_VERSION_KHR
         // otherwise use EGL_CONTEXT_CLIENT_VERSION

         int majorGLVersion = eglContextGLESVersion.getMajorVersion();
         int minorGLVersion = eglContextGLESVersion.getMinorVersion();

         int majorMinorAttribList[] = new int[]{ EGLExt.EGL_CONTEXT_MAJOR_VERSION_KHR, majorGLVersion,
          EGLExt.EGL_CONTEXT_MINOR_VERSION_KHR, minorGLVersion, EGL14.EGL_NONE };

         int majorAttribList[] = new int[]{ EGL14.EGL_CONTEXT_CLIENT_VERSION, majorGLVersion, EGL14.EGL_NONE };

         EGLContext context = null;

         if (minorGLVersion != 0)
         {
            context = EGL14.eglCreateContext(display, config, EGL14.EGL_NO_CONTEXT,
             majorGLVersion != 0 ? majorMinorAttribList : null, 0);
         }

         if (context == null || context.equals(EGL14.EGL_NO_CONTEXT))
         {
            Log.d(s_TAG, "DefaultContextFactory - createContext: Could create requested context: "
             +eglContextGLESVersion);

            Log.d(s_TAG, "DefaultContextFactory - createContest: " +
             "Attempting to create context based on major GLES version: " +majorGLVersion);

            context = EGL14.eglCreateContext(display, config, EGL14.EGL_NO_CONTEXT,
             majorGLVersion != 0 ? majorAttribList : null, 0);

            minorGLVersion = 0;
         }

         int clientValue[] = new int[1];

         EGL14.eglQueryContext(display, context, EGLExt.EGL_CONTEXT_MAJOR_VERSION_KHR, clientValue, 0);
         if (EGL14.eglGetError() != EGL14.EGL_BAD_ATTRIBUTE)
         {
            majorGLVersion = clientValue[0];
         }

         EGL14.eglQueryContext(display, context, EGLExt.EGL_CONTEXT_MINOR_VERSION_KHR, clientValue, 0);
         if (EGL14.eglGetError() != EGL14.EGL_BAD_ATTRIBUTE)
         {
            minorGLVersion = clientValue[0];
         }

         GLSurfaceView2.this.actualEGLContextGLESVersion = AndroidGLESUtil.getGLVersion(majorGLVersion, minorGLVersion);

         return context;
      }

      public void destroyContext(EGLDisplay display, EGLContext context)
      {
         if (!EGL14.eglDestroyContext(display, context))
         {
            Log.e("DefaultContextFactory", "display:" + display + " context: " + context);

            if (s_LOG_THREADS)
            {
               Log.i("DefaultContextFactory", "tid=" + Thread.currentThread().getId());
            }

            EglHelper.throwEglException("eglDestroyContex", EGL14.eglGetError());
         }
      }
   }

   /**
    * An interface for customizing the eglCreateWindowSurface and eglDestroySurface calls.
    * <p/>
    * This interface must be implemented by clients wishing to call
    * {@link GLSurfaceView2#setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory)}
    */
   public interface EGLWindowSurfaceFactory
   {
      /**
       * @return null if the surface cannot be constructed.
       */
      EGLSurface createWindowSurface(EGLDisplay display, EGLConfig config, Object nativeWindow);

      void destroySurface(EGLDisplay display, EGLSurface surface);
   }

   private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory
   {
      public EGLSurface createWindowSurface(EGLDisplay display, EGLConfig config, Object nativeWindow)
      {
         EGLSurface result = null;
         try
         {
            result = EGL14.eglCreateWindowSurface(display, config, nativeWindow, s_DEFAULT_SURFACE_ATTRIBS, 0);
         }
         catch (IllegalArgumentException e)
         {
            // This exception indicates that the surface flinger surface
            // is not valid. This can happen if the surface flinger surface has
            // been torn down, but the application has not yet been
            // notified via SurfaceHolder.Callback.surfaceDestroyed.
            // In theory the application should be notified first,
            // but in practice sometimes it is not. See b/4588890
            Log.e(s_TAG, "eglCreateWindowSurface", e);
         }
         return result;
      }

      public void destroySurface(EGLDisplay display, EGLSurface surface)
      {
         EGL14.eglDestroySurface(display, surface);
      }
   }

   /**
    * An interface for choosing an EGLConfig configuration from a list of
    * potential configurations.
    * <p/>
    * This interface must be implemented by clients wishing to call
    * {@link GLSurfaceView2#setEGLConfigChooser(EGLConfigChooser)}
    */
   public interface EGLConfigChooser
   {
      /**
       * Choose a configuration from the list. Implementors typically
       * implement this method by calling
       * {@link javax.microedition.khronos.egl.EGL10#eglChooseConfig} and iterating through the results. Please consult the
       * EGL specification available from The Khronos Group to learn how to call eglChooseConfig.
       *
       * @param display the current display.
       * @return the chosen configuration.
       */
      EGLConfig chooseConfig(EGLDisplay display);
   }

   private abstract class BaseConfigChooser implements EGLConfigChooser
   {
      protected int[] configSpec;

      public BaseConfigChooser(int[] configSpec)
      {
         this.configSpec = filterConfigSpec(configSpec);
      }

      public EGLConfig chooseConfig(EGLDisplay display)
      {
         EGLConfig[] configs = new EGLConfig[1];

         int[] num_config = new int[1];

         if (!EGL14.eglChooseConfig(display, configSpec, 0, configs, 0, configs.length, num_config, 0))
         {
            throw new IllegalArgumentException("eglChooseConfig failed");
         }

         int numConfigs = num_config[0];

         if (numConfigs <= 0)
         {
            throw new IllegalArgumentException("No configs match configSpec");
         }

         if (!EGL14.eglChooseConfig(display, configSpec, 0, configs, 0, configs.length, num_config, 0))
         {
            throw new IllegalArgumentException("eglChooseConfig#2 failed");
         }

         EGLConfig config = chooseConfig(display, configs);
         if (config == null)
         {
            throw new IllegalArgumentException("No config chosen");
         }

         return config;
      }

      abstract EGLConfig chooseConfig(EGLDisplay display, EGLConfig[] configs);

      private int[] filterConfigSpec(int[] configSpec)
      {
         int majorGLVersion = eglContextGLESVersion.getMajorVersion();

         if (majorGLVersion != 2 && majorGLVersion != 3)
         {
            return configSpec;
         }
            /* We know none of the subclasses define EGL_RENDERABLE_TYPE.
             * And we know the configSpec is well formed.
             */
         int len = configSpec.length;
         int[] newConfigSpec = new int[len + 2];
         System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
         newConfigSpec[len - 1] = EGL14.EGL_RENDERABLE_TYPE;
         if (majorGLVersion == 2)
         {
            newConfigSpec[len] = EGL14.EGL_OPENGL_ES2_BIT;  /* EGL_OPENGL_ES2_BIT */
         }
         else
         {
            newConfigSpec[len] = EGLExt.EGL_OPENGL_ES3_BIT_KHR; /* EGL_OPENGL_ES3_BIT_KHR */
         }
         newConfigSpec[len + 1] = EGL14.EGL_NONE;
         return newConfigSpec;
      }
   }

   /**
    * Choose a configuration with exactly the specified r,g,b,a sizes,
    * and at least the specified depth and stencil sizes.
    */
   private class ComponentSizeChooser extends BaseConfigChooser
   {
      private int[] value;

      // Subclasses can adjust these values:
      protected int redSize;
      protected int greenSize;
      protected int blueSize;
      protected int alphaSize;
      protected int depthSize;
      protected int stencilSize;

      public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize,
       int stencilSize)
      {
         super(new int[]{
          EGL14.EGL_RED_SIZE, redSize,
          EGL14.EGL_GREEN_SIZE, greenSize,
          EGL14.EGL_BLUE_SIZE, blueSize,
          EGL14.EGL_ALPHA_SIZE, alphaSize,
          EGL14.EGL_DEPTH_SIZE, depthSize,
          EGL14.EGL_STENCIL_SIZE, stencilSize,
          EGL14.EGL_NONE});

         value = new int[1];
         this.redSize = redSize;
         this.greenSize = greenSize;
         this.blueSize = blueSize;
         this.alphaSize = alphaSize;
         this.depthSize = depthSize;
         this.stencilSize = stencilSize;
      }

      @Override
      public EGLConfig chooseConfig(EGLDisplay display, EGLConfig[] configs)
      {
         for (EGLConfig config : configs)
         {
            int d = findConfigAttrib(display, config, EGL14.EGL_DEPTH_SIZE, 0);
            int s = findConfigAttrib(display, config, EGL14.EGL_STENCIL_SIZE, 0);

            if ((d >= depthSize) && (s >= stencilSize))
            {
               int r = findConfigAttrib(display, config, EGL14.EGL_RED_SIZE, 0);
               int g = findConfigAttrib(display, config, EGL14.EGL_GREEN_SIZE, 0);
               int b = findConfigAttrib(display, config, EGL14.EGL_BLUE_SIZE, 0);
               int a = findConfigAttrib(display, config, EGL14.EGL_ALPHA_SIZE, 0);

               if ((r == redSize) && (g == greenSize) && (b == blueSize) && (a == alphaSize))
               {
                  return config;
               }
            }
         }
         return null;
      }

      private int findConfigAttrib(EGLDisplay display, EGLConfig config, int attribute, int defaultValue)
      {
         if (EGL14.eglGetConfigAttrib(display, config, attribute, value, 0))
         {
            return value[0];
         }
         return defaultValue;
      }
   }

   /**
    * This class will choose a RGB_888 surface with or without a depth buffer.
    */
   private class SimpleEGLConfigChooser extends ComponentSizeChooser
   {
      public SimpleEGLConfigChooser(boolean withDepthBuffer)
      {
         super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
      }
   }

   /**
    * An EGL helper class.
    */

   private static class EglHelper
   {
      private WeakReference<GLSurfaceView2>  mGLSurfaceViewWeakRef;
      EGLDisplay                             mEglDisplay;
      EGLSurface                             mEglSurface;
      EGLConfig                              mEglConfig;
      EGLContext                             mEglContext;

      public EglHelper(WeakReference<GLSurfaceView2> glSurfaceViewWeakRef)
      {
         mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
      }

      /**
       * Initialize EGL for a given configuration spec.
       */
      public void start()
      {
         if (s_LOG_EGL)
         {
            Log.w("EglHelper", "start() tid=" + Thread.currentThread().getId());
         }

            /*
             * Get to the default display.
             */
         mEglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);

         if (mEglDisplay == EGL14.EGL_NO_DISPLAY)
         {
            throw new RuntimeException("eglGetDisplay failed");
         }

            /*
             * We can now initialize EGL for that display
             */
         int[] version = new int[2];
         if (!EGL14.eglInitialize(mEglDisplay, version, 0, version, 1))
         {
            throw new RuntimeException("eglInitialize failed");
         }

         GLSurfaceView2 view = mGLSurfaceViewWeakRef.get();

         if (view == null)
         {
            mEglConfig = null;
            mEglContext = null;
         }
         else
         {
            mEglConfig = view.eglConfigChooser.chooseConfig(mEglDisplay);

                /*
                * Create an EGL context. We want to do this as rarely as we can, because an
                * EGL context is a somewhat heavy object.
                */
            mEglContext = view.eglContextFactory.createContext(mEglDisplay, mEglConfig);
         }

         if (mEglContext == null || mEglContext.equals(EGL14.EGL_NO_CONTEXT))
         {
            mEglContext = null;
            throwEglException("createContext");
         }

         // Store major / minor OpenGL ES versions by querying the new context.

         int majorGLVersion = -1, minorGLVersion = -1;

         int clientValue[] = new int[1];
         EGL14.eglQueryContext(mEglDisplay, mEglContext, EGLExt.EGL_CONTEXT_MAJOR_VERSION_KHR, clientValue, 0);
         if (EGL14.eglGetError() != EGL14.EGL_BAD_ATTRIBUTE)
         {
            majorGLVersion = clientValue[0];
         }

         EGL14.eglQueryContext(mEglDisplay, mEglContext, EGLExt.EGL_CONTEXT_MINOR_VERSION_KHR, clientValue, 0);
         if (EGL14.eglGetError() != EGL14.EGL_BAD_ATTRIBUTE)
         {
            minorGLVersion = clientValue[0];
         }

         // Some EGL implementations like ARM Mali circa Q2 2015 don't support EGL_CONTEXT_MINOR_VERSION_KHR
         // Only set if major / minor queries succeed.
         if (view != null)
         {
            if (majorGLVersion >= 0 && minorGLVersion >= 0)
            {
               // Set actual IGLVersion
               view.actualEGLContextGLESVersion = AndroidGLESUtil.getGLVersion(majorGLVersion, minorGLVersion);
            }
            else
            {
               majorGLVersion = view.actualEGLContextGLESVersion.getMajorVersion();
               minorGLVersion = view.actualEGLContextGLESVersion.getMinorVersion();
            }
         }

         // Post debug message indicating the major / minor version of the GL Context created.
         Log.d(s_TAG, "EglHelper - createContext - major version: " +majorGLVersion +"; minor version: " +minorGLVersion);

         if (s_LOG_EGL)
         {
            Log.w("EglHelper", "createContext " + mEglContext + " tid=" + Thread.currentThread().getId());
         }

         mEglSurface = null;

         // Callback to inform that a context has been created.
         if (view != null)
         {
            view.renderer.onGLContextCreated();
         }
      }

      /**
       * Create an egl surface for the current SurfaceHolder surface. If a surface
       * already exists, destroy it before creating the new surface.
       *
       * @return true if the surface was created successfully.
       */
      public boolean createSurface()
      {
         if (s_LOG_EGL)
         {
            Log.w("EglHelper", "createSurface()  tid=" + Thread.currentThread().getId());
         }
            /*
             * Check preconditions.
             */
         if (mEglDisplay == null)
         {
            throw new RuntimeException("eglDisplay not initialized");
         }

         if (mEglConfig == null)
         {
            throw new RuntimeException("mEglConfig not initialized");
         }

            /*
             *  The window size has changed, so we need to create a new
             *  surface.
             */
         destroySurfaceImp();

            /*
             * Create an EGL surface we can render into.
             */
         GLSurfaceView2 view = mGLSurfaceViewWeakRef.get();
         if (view != null)
         {
            mEglSurface = view.eglWindowSurfaceFactory.createWindowSurface(mEglDisplay, mEglConfig, view.getHolder());
         }
         else
         {
            mEglSurface = null;
         }

         if (mEglSurface == null || mEglSurface == EGL14.EGL_NO_SURFACE)
         {
            int error = EGL14.eglGetError();
            if (error == EGL14.EGL_BAD_NATIVE_WINDOW)
            {
               Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
            }
            return false;
         }

         /*
          * Before we can issue GL commands, we need to make sure
          * the context is current and bound to a surface.
          */
         if (!EGL14.eglMakeCurrent(mEglDisplay, mEglSurface, mEglSurface, mEglContext))
         {
            /*
             * Could not make the context current, probably because the underlying
             * SurfaceView surface has been destroyed.
             */
            logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", EGL14.eglGetError());
            return false;
         }

         return true;
      }

      /**
       * Display the current render surface.
       *
       * @return the EGL error code from eglSwapBuffers.
       */
      public int swap()
      {
         if (!EGL14.eglSwapBuffers(mEglDisplay, mEglSurface))
         {
            return EGL14.eglGetError();
         }
         return EGL14.EGL_SUCCESS;
      }

      public void destroySurface()
      {
         if (s_LOG_EGL)
         {
            Log.w("EglHelper", "destroySurface()  tid=" + Thread.currentThread().getId());
         }
         destroySurfaceImp();
      }

      private void destroySurfaceImp()
      {
         if (mEglSurface != null && mEglSurface != EGL14.EGL_NO_SURFACE)
         {
            EGL14.eglMakeCurrent(mEglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            GLSurfaceView2 view = mGLSurfaceViewWeakRef.get();

            if (view != null)
            {
               view.eglWindowSurfaceFactory.destroySurface(mEglDisplay, mEglSurface);
            }
            mEglSurface = null;
         }
      }

      public void finish()
      {
         if (s_LOG_EGL)
         {
            Log.w("EglHelper", "finish() tid=" + Thread.currentThread().getId());
         }

         if (mEglContext != null)
         {
            GLSurfaceView2 view = mGLSurfaceViewWeakRef.get();

            if (view != null)
            {
               view.eglContextFactory.destroyContext(mEglDisplay, mEglContext);
               view.actualEGLContextGLESVersion = XeGLUnknown.GL_UNKNOWN;
            }
            mEglContext = null;
         }

         if (mEglDisplay != null)
         {
            EGL14.eglTerminate(mEglDisplay);
            mEglDisplay = null;
         }
      }

      private void throwEglException(String function)
      {
         throwEglException(function, EGL14.eglGetError());
      }

      public static void throwEglException(String function, int error)
      {
         String message = formatEglError(function, error);
         if (s_LOG_THREADS)
         {
            Log.e("EglHelper", "throwEglException tid=" +Thread.currentThread().getId() +" " +message);
         }
         throw new RuntimeException(message);
      }

      public static void logEglErrorAsWarning(String tag, String function, int error)
      {
         Log.w(tag, formatEglError(function, error));
      }

      public static String formatEglError(String function, int error)
      {
         return function +" failed: " +error; // TODO EGLLogWrapper.getErrorString(error);
      }
   }

   /**
    * A generic GL Thread. Takes care of initializing EGL and GL. Delegates
    * to a Renderer instance to do the actual drawing. Can be configured to
    * render continuously or on request.
    * <p/>
    * All potentially blocking synchronization is done through the
    * s_GLThreadManager object. This avoids multiple-lock ordering issues.
    */
   static class GLThread extends Thread
   {
      // Once the thread is started, all accesses to the following member
      // variables are protected by the s_GLThreadManager monitor
      private boolean               shouldExit;
      private boolean               exited;
      private boolean               requestPaused;
      private boolean               paused;
      private boolean               hasSurface;
      private boolean               surfaceIsBad;
      private boolean               waitingForSurface;
      private boolean               haveEglContext;
      private boolean               haveEglSurface;
      private boolean               finishedCreatingEglSurface;
      private boolean               shouldReleaseEglContext;
      private int                   width;
      private int                   height;
      private int                   renderMode;
      private boolean               requestRender;
      private boolean               renderComplete;
      private ArrayList<Runnable>   eventQueue = new ArrayList<Runnable>();
      private boolean               sizeChanged = true;

      // End of member variables protected by the s_GLThreadManager monitor.

      private EglHelper             eglHelper;

      /**
       * Set once at thread construction time, nulled out when the parent view is garbage
       * called. This weak reference allows the GLSurfaceView2 to be garbage collected while
       * the GLThread is still alive.
       */
      private WeakReference<GLSurfaceView2>  glSurfaceViewWeakRef;

      GLThread(WeakReference<GLSurfaceView2> glSurfaceViewWeakRef)
      {
         super();
         width = 0;
         height = 0;
         requestRender = true;
         renderMode = s_RENDERMODE_CONTINUOUSLY;
         this.glSurfaceViewWeakRef = glSurfaceViewWeakRef;
      }

      @Override
      public void run()
      {
         setName("GLThread " + getId());
         if (s_LOG_THREADS)
         {
            Log.i("GLThread", "starting tid=" + getId());
         }

         try
         {
            guardedRun();
         }
         catch (InterruptedException e)
         {
            // fall thru and exit normally
         }
         finally
         {
            s_GLThreadManager.threadExiting(this);
         }
      }

      /*
       * This private method should only be called inside a
       * synchronized(s_GLThreadManager) block.
       */
      private void stopEglSurfaceLocked()
      {
         if (haveEglSurface)
         {
            haveEglSurface = false;
            eglHelper.destroySurface();
         }
      }

      /*
       * This private method should only be called inside a
       * synchronized(s_GLThreadManager) block.
       */
      private void stopEglContextLocked()
      {
         if (haveEglContext)
         {
            eglHelper.finish();
            haveEglContext = false;
            s_GLThreadManager.releaseEglContextLocked(this);
         }
      }

      private void guardedRun() throws InterruptedException
      {
         eglHelper = new EglHelper(glSurfaceViewWeakRef);
         haveEglContext = false;
         haveEglSurface = false;
         try
         {
            boolean createEglContext = false;
            boolean createEglSurface = false;
            boolean lostEglContext = false;
            boolean sizeChanged = false;
            boolean wantRenderNotification = false;
            boolean doRenderNotification = false;
            boolean askedToReleaseEglContext = false;
            int w = 0;
            int h = 0;
            Runnable event = null;

            while (true)
            {
               synchronized (s_GLThreadManager)
               {
                  while (true)
                  {
                     if (shouldExit)
                     {
                        return;
                     }

                     if (!eventQueue.isEmpty())
                     {
                        event = eventQueue.remove(0);
                        break;
                     }

                     // Update the pause state.
                     boolean pausing = false;
                     if (paused != requestPaused)
                     {
                        pausing = requestPaused;
                        paused = requestPaused;
                        s_GLThreadManager.notifyAll();
                        if (s_LOG_PAUSE_RESUME)
                        {
                           Log.i("GLThread", "paused is now " + paused + " tid=" + getId());
                        }
                     }

                     // Do we need to give up the EGL context?
                     if (shouldReleaseEglContext)
                     {
                        if (s_LOG_SURFACE)
                        {
                           Log.i("GLThread", "releasing EGL context because asked to tid=" + getId());
                        }
                        stopEglSurfaceLocked();
                        stopEglContextLocked();
                        shouldReleaseEglContext = false;
                        askedToReleaseEglContext = true;
                     }

                     // Have we lost the EGL context?
                     if (lostEglContext)
                     {
                        stopEglSurfaceLocked();
                        stopEglContextLocked();
                        lostEglContext = false;
                     }

                     // When pausing, release the EGL surface:
                     if (pausing && haveEglSurface)
                     {
                        if (s_LOG_SURFACE)
                        {
                           Log.i("GLThread", "releasing EGL surface because paused tid=" + getId());
                        }
                        stopEglSurfaceLocked();
                     }

                     // When pausing, optionally release the EGL Context:
                     if (pausing && haveEglContext)
                     {
                        GLSurfaceView2 view = glSurfaceViewWeakRef.get();
                        boolean preserveEglContextOnPause = view != null && view.preserveEGLContextOnPause;
                        if (!preserveEglContextOnPause)
                        {
                           stopEglContextLocked();
                           if (s_LOG_SURFACE)
                           {
                              Log.i("GLThread", "releasing EGL context because paused tid=" + getId());
                           }
                        }
                     }

                     // Have we lost the SurfaceView surface?
                     if ((!hasSurface) && (!waitingForSurface))
                     {
                        if (s_LOG_SURFACE)
                        {
                           Log.i("GLThread", "noticed surfaceView surface lost tid=" + getId());
                        }

                        if (haveEglSurface)
                        {
                           stopEglSurfaceLocked();
                        }

                        waitingForSurface = true;
                        surfaceIsBad = false;
                        s_GLThreadManager.notifyAll();
                     }

                     // Have we acquired the surface view surface?
                     if (hasSurface && waitingForSurface)
                     {
                        if (s_LOG_SURFACE)
                        {
                           Log.i("GLThread", "noticed surfaceView surface acquired tid=" + getId());
                        }
                        waitingForSurface = false;
                        s_GLThreadManager.notifyAll();
                     }

                     if (doRenderNotification)
                     {
                        if (s_LOG_SURFACE)
                        {
                           Log.i("GLThread", "sending render notification tid=" + getId());
                        }
                        wantRenderNotification = false;
                        doRenderNotification = false;
                        renderComplete = true;
                        s_GLThreadManager.notifyAll();
                     }

                     // Ready to draw?
                     if (readyToDraw())
                     {

                        // If we don't have an EGL context, try to acquire one.
                        if (!haveEglContext)
                        {
                           if (askedToReleaseEglContext)
                           {
                              askedToReleaseEglContext = false;
                           }
                           else if (s_GLThreadManager.tryAcquireEglContextLocked(this))
                           {
                              try
                              {
                                 eglHelper.start();
                              }
                              catch (RuntimeException t)
                              {
                                 s_GLThreadManager.releaseEglContextLocked(this);
                                 throw t;
                              }
                              haveEglContext = true;
                              createEglContext = true;

                              s_GLThreadManager.notifyAll();
                           }
                        }

                        if (haveEglContext && !haveEglSurface)
                        {
                           haveEglSurface = true;
                           createEglSurface = true;
                           sizeChanged = true;
                        }

                        if (haveEglSurface)
                        {
                           if (this.sizeChanged)
                           {
                              sizeChanged = true;
                              w = width;
                              h = height;
                              wantRenderNotification = true;
                              if (s_LOG_SURFACE)
                              {
                                 Log.i("GLThread",
                                  "noticing that we want render notification tid="
                                   + getId());
                              }

                              // Destroy and recreate the EGL surface.
                              createEglSurface = true;

                              this.sizeChanged = false;
                           }
                           requestRender = false;
                           s_GLThreadManager.notifyAll();
                           break;
                        }
                     }

                     // By design, this is the only place in a GLThread thread where we wait().
                     if (s_LOG_THREADS)
                     {
                        Log.i("GLThread", "waiting tid=" + getId()
                         + " haveEglContext: " + haveEglContext
                         + " haveEglSurface: " + haveEglSurface
                         + " finishedCreatingEglSurface: " + finishedCreatingEglSurface
                         + " paused: " + paused
                         + " hasSurface: " + hasSurface
                         + " surfaceIsBad: " + surfaceIsBad
                         + " waitingForSurface: " + waitingForSurface
                         + " width: " + width
                         + " height: " + height
                         + " requestRender: " + requestRender
                         + " renderMode: " + renderMode);
                     }
                     s_GLThreadManager.wait();
                  }
               } // end of synchronized(s_GLThreadManager)

               if (event != null)
               {
                  event.run();
                  event = null;
                  continue;
               }

               if (createEglSurface)
               {
                  if (s_LOG_SURFACE)
                  {
                     Log.w("GLThread", "egl createSurface");
                  }

                  if (eglHelper.createSurface())
                  {
                     synchronized (s_GLThreadManager)
                     {
                        finishedCreatingEglSurface = true;
                        s_GLThreadManager.notifyAll();
                     }
                  }
                  else
                  {
                     synchronized (s_GLThreadManager)
                     {
                        finishedCreatingEglSurface = true;
                        surfaceIsBad = true;
                        s_GLThreadManager.notifyAll();
                     }
                     continue;
                  }
                  createEglSurface = false;
               }

               if (createEglContext)
               {
                  if (s_LOG_RENDERER)
                  {
                     Log.w("GLThread", "onGLSurfaceCreated");
                  }

                  GLSurfaceView2 view = glSurfaceViewWeakRef.get();
                  if (view != null)
                  {
                     view.renderer.onGLSurfaceCreated();
                  }
                  createEglContext = false;
               }

               if (sizeChanged)
               {
                  if (s_LOG_RENDERER)
                  {
                     Log.w("GLThread", "onGLSurfaceChanged(" + w + ", " + h + ")");
                  }

                  GLSurfaceView2 view = glSurfaceViewWeakRef.get();
                  if (view != null)
                  {
                     view.renderer.onGLSurfaceChanged(w, h);
                  }
                  sizeChanged = false;
               }

               if (s_LOG_RENDERER_DRAW_FRAME)
               {
                  Log.w("GLThread", "onGLDrawFrame tid=" + getId());
               }
               {
                  GLSurfaceView2 view = glSurfaceViewWeakRef.get();
                  if (view != null)
                  {
                     view.renderer.onGLDrawFrame();
                  }
               }
               int swapError = eglHelper.swap();
               switch (swapError)
               {
                  case EGL14.EGL_SUCCESS:
                     break;
                  case EGL14.EGL_CONTEXT_LOST:
                     if (s_LOG_SURFACE)
                     {
                        Log.i("GLThread", "egl context lost tid=" + getId());
                     }
                     lostEglContext = true;
                     break;
                  default:
                     // Other errors typically mean that the current surface is bad,
                     // probably because the SurfaceView surface has been destroyed,
                     // but we haven't been notified yet.
                     // Log the error to help developers understand why rendering stopped.
                     eglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", swapError);

                     synchronized (s_GLThreadManager)
                     {
                        surfaceIsBad = true;
                        s_GLThreadManager.notifyAll();
                     }
                     break;
               }

               if (wantRenderNotification)
               {
                  doRenderNotification = true;
               }
            }

         }
         finally
         {
                /*
                 * clean-up everything...
                 */
            synchronized (s_GLThreadManager)
            {
               stopEglSurfaceLocked();
               stopEglContextLocked();
            }
         }
      }

      public boolean ableToDraw()
      {
         return haveEglContext && haveEglSurface && readyToDraw();
      }

      private boolean readyToDraw()
      {
         return (!paused) && hasSurface && (!surfaceIsBad) && (width > 0) && (height > 0) &&
          (requestRender || (renderMode == s_RENDERMODE_CONTINUOUSLY));
      }

      public void setRenderMode(int renderMode)
      {
         if (!((s_RENDERMODE_WHEN_DIRTY <= renderMode) && (renderMode <= s_RENDERMODE_CONTINUOUSLY)))
         {
            throw new IllegalArgumentException("renderMode");
         }
         synchronized (s_GLThreadManager)
         {
            this.renderMode = renderMode;
            s_GLThreadManager.notifyAll();
         }
      }

      public int getRenderMode()
      {
         synchronized (s_GLThreadManager)
         {
            return renderMode;
         }
      }

      public void requestRender()
      {
         synchronized (s_GLThreadManager)
         {
            requestRender = true;
            s_GLThreadManager.notifyAll();
         }
      }

      public void surfaceCreated()
      {
         synchronized (s_GLThreadManager)
         {
            if (s_LOG_THREADS)
            {
               Log.i("GLThread", "surfaceCreated tid=" + getId());
            }
            hasSurface = true;
            finishedCreatingEglSurface = false;
            s_GLThreadManager.notifyAll();
            while (waitingForSurface && !finishedCreatingEglSurface && !exited)
            {
               try
               {
                  s_GLThreadManager.wait();
               }
               catch (InterruptedException e)
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      public void surfaceDestroyed()
      {
         synchronized (s_GLThreadManager)
         {
            if (s_LOG_THREADS)
            {
               Log.i("GLThread", "surfaceDestroyed tid=" + getId());
            }
            hasSurface = false;
            s_GLThreadManager.notifyAll();
            while ((!waitingForSurface) && (!exited))
            {
               try
               {
                  s_GLThreadManager.wait();
               }
               catch (InterruptedException e)
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      public void onPause()
      {
         synchronized (s_GLThreadManager)
         {
            if (s_LOG_PAUSE_RESUME)
            {
               Log.i("GLThread", "onPause tid=" + getId());
            }
            requestPaused = true;
            s_GLThreadManager.notifyAll();
            while ((!exited) && (!paused))
            {
               if (s_LOG_PAUSE_RESUME)
               {
                  Log.i("Main thread", "onPause waiting for paused.");
               }
               try
               {
                  s_GLThreadManager.wait();
               }
               catch (InterruptedException ex)
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      public void onResume()
      {
         synchronized (s_GLThreadManager)
         {
            if (s_LOG_PAUSE_RESUME)
            {
               Log.i("GLThread", "onResume tid=" + getId());
            }
            requestPaused = false;
            requestRender = true;
            renderComplete = false;
            s_GLThreadManager.notifyAll();
            while ((!exited) && paused && (!renderComplete))
            {
               if (s_LOG_PAUSE_RESUME)
               {
                  Log.i("Main thread", "onResume waiting for !paused.");
               }
               try
               {
                  s_GLThreadManager.wait();
               }
               catch (InterruptedException ex)
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      public void onWindowResize(int w, int h)
      {
         synchronized (s_GLThreadManager)
         {
            width = w;
            height = h;
            sizeChanged = true;
            requestRender = true;
            renderComplete = false;
            s_GLThreadManager.notifyAll();

            // Wait for thread to react to resize and render a frame
            while (!exited && !paused && !renderComplete && ableToDraw())
            {
               if (s_LOG_SURFACE)
               {
                  Log.i("Main thread", "onWindowResize waiting for render complete from tid=" + getId());
               }
               try
               {
                  s_GLThreadManager.wait();
               }
               catch (InterruptedException ex)
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      public void setPresentationTime(long timestamp)
      {
         EGLExt.eglPresentationTimeANDROID(eglHelper.mEglDisplay, eglHelper.mEglSurface, timestamp);
      }

      public void requestExitAndWait()
      {
         // don't call this from GLThread thread or it is a guaranteed
         // deadlock!
         synchronized (s_GLThreadManager)
         {
            shouldExit = true;
            s_GLThreadManager.notifyAll();
            while (!exited)
            {
               try
               {
                  s_GLThreadManager.wait();
               }
               catch (InterruptedException ex)
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      public void requestReleaseEglContextLocked()
      {
         shouldReleaseEglContext = true;
         s_GLThreadManager.notifyAll();
      }

      /**
       * Queue an "event" to be run on the GL rendering thread.
       *
       * @param r the runnable to be run on the GL rendering thread.
       */
      public void queueEvent(Runnable r)
      {
         if (r == null)
         {
            throw new IllegalArgumentException("r must not be null");
         }
         synchronized (s_GLThreadManager)
         {
            eventQueue.add(r);
            s_GLThreadManager.notifyAll();
         }
      }
   }

   private void checkRenderThreadState()
   {
      if (glThread != null)
      {
         throw new IllegalStateException(
          "setRenderer has already been called for this instance.");
      }
   }

   private static class GLThreadManager
   {
      private static String   s_TAG = "GLThreadManager";

      private GLThread        eglOwner;

      public synchronized void threadExiting(GLThread thread)
      {
         if (s_LOG_THREADS)
         {
            Log.i(s_TAG, "exiting tid=" + thread.getId());
         }
         thread.exited = true;
         if (eglOwner == thread)
         {
            eglOwner = null;
         }
         notifyAll();
      }

      /*
       * Tries once to acquire the right to use an EGL
       * context. Does not block. Requires that we are already
       * in the s_GLThreadManager monitor when this is called.
       *
       * @return true if the right to use an EGL context was acquired.
       */
      public boolean tryAcquireEglContextLocked(GLThread thread)
      {
         if (eglOwner == thread || eglOwner == null)
         {
            eglOwner = thread;
            notifyAll();
            return true;
         }

         return true;
      }

      /*
       * Releases the EGL context. Requires that we are already in the
       * s_GLThreadManager monitor when this is called.
       */
      public void releaseEglContextLocked(GLThread thread)
      {
         if (eglOwner == thread)
         {
            eglOwner = null;
         }
         notifyAll();
      }
   }
}
