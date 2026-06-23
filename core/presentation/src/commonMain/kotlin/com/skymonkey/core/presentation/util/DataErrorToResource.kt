package com.skymonkey.core.presentation.util

import chirpclient.core.presentation.generated.resources.Res
import chirpclient.core.presentation.generated.resources.error_bad_request
import chirpclient.core.presentation.generated.resources.error_conflict
import chirpclient.core.presentation.generated.resources.error_disk_full
import chirpclient.core.presentation.generated.resources.error_forbidden
import chirpclient.core.presentation.generated.resources.error_no_internet
import chirpclient.core.presentation.generated.resources.error_not_found
import chirpclient.core.presentation.generated.resources.error_payload_too_large
import chirpclient.core.presentation.generated.resources.error_request_timeout
import chirpclient.core.presentation.generated.resources.error_serialization
import chirpclient.core.presentation.generated.resources.error_server
import chirpclient.core.presentation.generated.resources.error_service_unavailable
import chirpclient.core.presentation.generated.resources.error_too_many_requests
import chirpclient.core.presentation.generated.resources.error_unauthorized
import chirpclient.core.presentation.generated.resources.error_unknown
import com.skymonkey.core.domain.util.DataError
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

fun DataError.toStringResource(): StringResource {
    val resourceId = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.FILE_NOT_FOUND -> Res.string.error_not_found
        DataError.Local.DATABASE_ERROR -> Res.string.error_unknown
        DataError.Local.CACHE_ERROR -> Res.string.error_unknown
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.BAD_REQUEST -> Res.string.error_bad_request
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.UNAUTHORIZED -> Res.string.error_unauthorized
        DataError.Remote.FORBIDDEN -> Res.string.error_forbidden
        DataError.Remote.NOT_FOUND -> Res.string.error_not_found
        DataError.Remote.CONFLICT -> Res.string.error_conflict
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET_CONNECTION -> Res.string.error_no_internet
        DataError.Remote.PAYLOAD_TOO_LARGE -> Res.string.error_payload_too_large
        DataError.Remote.INTERNAL_SERVER_ERROR -> Res.string.error_server
        DataError.Remote.SERVICE_UNAVAILABLE -> Res.string.error_service_unavailable
        DataError.Remote.SERIALIZATION_ERROR -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }
    return resourceId
}