package main.java;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
@SuppressWarnings("rawtypes")
public class HttpServiceTracker extends ServiceTracker{
    @SuppressWarnings("unchecked")
	public HttpServiceTracker(BundleContext context) {
        super(context, HttpService.class.getName(), null);
    }
    @Override
	public Object addingService(ServiceReference reference) {
        @SuppressWarnings("unchecked")
		HttpService httpService = (HttpService) context.getService(reference);
        try {
        	httpService.registerResources("/js/angular.min.js", "WebContent/js/angular.min.js", null);
        	httpService.registerResources("/js/angular.min.js.map", "WebContent/js/angular.min.js.map", null);
        	httpService.registerResources("/js/app.js", "WebContent/js/app.js", null);
            httpService.registerResources("/css/bootstrap.min.css", "WebContent/css/bootstrap.min.css", null);
            httpService.registerResources("/css/app.css", "WebContent/css/app.css", null);
            httpService.registerResources("/index.html", "WebContent/index.html", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpService;
    }
    @SuppressWarnings("unchecked")
	@Override
	public void removedService(ServiceReference reference, Object service) {
        HttpService httpService = (HttpService) service;
        httpService.unregister("/index.html");
        httpService.unregister("/js/angular.min.js");
        httpService.unregister("/js/angular.min.js.map"); 
        httpService.unregister("/js/app.js");
        httpService.unregister("/css/bootstrap.min.css");
        httpService.unregister("/css/app.js");
        super.removedService(reference, service);
    }
}
