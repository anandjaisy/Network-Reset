package ramsay.health.shared;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Response<T>(T data, MetaResponse meta)
{
    public static <T>Response<T> Of(T data, MetaResponse meta)
    {
        return new Response<T>(data, meta);
    }
};