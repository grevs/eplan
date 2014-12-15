package osgi.rest.example;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

@SuppressWarnings("rawtypes")
public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceRegistration<ArticleService> articleService;
	private ServiceRegistration<ArticleProvider> articleProvider;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		articleService = bundleContext.registerService(ArticleService.class, new ArticleService(), null);
		articleProvider = bundleContext.registerService(ArticleProvider.class, new ArticleProvider(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		articleService.unregister();
		articleProvider.unregister();
	}

}
