-- example HTTP POST script which demonstrates setting the
-- HTTP method, body, and adding a header

request = function()
    print "function_request_2"
    local path = "/api/v1/users/filter?fNamePrefix=%D0%90%D0%BD%D0%B4%D1%80&lNamePrefix=%D0%90%D0%BD%D0%B4%D1%80"
    return wrk.format("GET", path, headers, body)
end

