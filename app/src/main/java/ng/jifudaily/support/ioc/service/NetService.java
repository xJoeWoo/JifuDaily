package ng.jifudaily.support.ioc.service;

/**
 * Created by Ng on 2017/4/20.
 */

public interface NetService {
    <T> T Create(String baseUrl, Class<T> service);
}
