package example.shared.auth;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpError;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

public class DefaultAuthenticationService implements AuthenticationService
{
    public static final String AUTH_HEADER_NAME = "MY-USER-ID";

    @Override
    public Observable<Boolean> authenticate(HttpServerRequest<ByteBuf> request)
    {
        if (request.getHeaders().contains(AUTH_HEADER_NAME))
        {
            return Observable.just(Boolean.TRUE);
        }
        else
        {
            return Observable.error(new HttpError(HttpResponseStatus.FORBIDDEN, "Should pass a header: " + AUTH_HEADER_NAME));
        }
    }
}