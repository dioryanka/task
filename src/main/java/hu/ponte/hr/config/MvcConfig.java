package hu.ponte.hr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class MvcConfig implements WebMvcConfigurer
{

	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/login").setViewName("login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		//static files
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

		//vue spa serve
		registry
			.addResourceHandler("/ui/**/*.css", "/ui/**/*.html", "/ui/**/*.js", "/ui/**/*.jsx", "/ui/**/*.png", "/ui/**/*.ttf", "/ui/**/*.woff", "/ui/**/*.woff2")
			.setCachePeriod(0)
			.addResourceLocations("classpath:/javascript/iq/");

		registry.addResourceHandler("/**")
			.setCachePeriod(-1)
			.addResourceLocations("classpath:/javascript/iq/index.html")
			.resourceChain(true)
			.addResolver(new PathResourceResolver() {
				@Override
				protected Resource getResource(String resourcePath, Resource location) throws IOException
				{
					String baseApiPath = "/ui";
					if (resourcePath.startsWith(baseApiPath) || resourcePath.startsWith(baseApiPath.substring(1))) {
						return location.exists() && location.isReadable() ? location : null;
					}

					return  null;
				}
			});
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
			.allowCredentials(true);
	}

}



