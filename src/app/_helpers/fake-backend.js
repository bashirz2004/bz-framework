"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.fakeBackendProvider = exports.FakeBackendInterceptor = void 0;
var core_1 = require("@angular/core");
var http_1 = require("@angular/common/http");
var rxjs_1 = require("rxjs");
var operators_1 = require("rxjs/operators");
// array in local storage for registered users
var usersKey = 'angular-10-registration-login-example-users';
var users = JSON.parse(localStorage.getItem(usersKey)) || [];
var FakeBackendInterceptor = /** @class */ (function () {
    function FakeBackendInterceptor() {
    }
    FakeBackendInterceptor.prototype.intercept = function (request, next) {
        var url = request.url, method = request.method, headers = request.headers, body = request.body;
        return handleRoute();
        function handleRoute() {
            switch (true) {
                case url.endsWith('/users/authenticate') && method === 'POST':
                    return authenticate();
                case url.endsWith('/users/register') && method === 'POST':
                    return register();
                case url.endsWith('/users') && method === 'GET':
                    return getUsers();
                case url.match(/\/users\/\d+$/) && method === 'GET':
                    return getUserById();
                case url.match(/\/users\/\d+$/) && method === 'PUT':
                    return updateUser();
                case url.match(/\/users\/\d+$/) && method === 'DELETE':
                    return deleteUser();
                default:
                    // pass through any requests not handled above
                    return next.handle(request);
            }
        }
        // route functions
        function authenticate() {
            var username = body.username, password = body.password;
            var user = users.find(function (x) { return x.username === username && x.password === password; });
            if (!user)
                return error('Username or password is incorrect');
            return ok(__assign(__assign({}, basicDetails(user)), { token: 'fake-jwt-token' }));
        }
        function register() {
            var user = body;
            if (users.find(function (x) { return x.username === user.username; })) {
                return error('Username "' + user.username + '" is already taken');
            }
            user.id = users.length ? Math.max.apply(Math, users.map(function (x) { return x.id; })) + 1 : 1;
            users.push(user);
            localStorage.setItem(usersKey, JSON.stringify(users));
            return ok();
        }
        function getUsers() {
            if (!isLoggedIn())
                return unauthorized();
            return ok(users.map(function (x) { return basicDetails(x); }));
        }
        function getUserById() {
            if (!isLoggedIn())
                return unauthorized();
            var user = users.find(function (x) { return x.id === idFromUrl(); });
            return ok(basicDetails(user));
        }
        function updateUser() {
            if (!isLoggedIn())
                return unauthorized();
            var params = body;
            var user = users.find(function (x) { return x.id === idFromUrl(); });
            // only update password if entered
            if (!params.password) {
                delete params.password;
            }
            // update and save user
            Object.assign(user, params);
            localStorage.setItem(usersKey, JSON.stringify(users));
            return ok();
        }
        function deleteUser() {
            if (!isLoggedIn())
                return unauthorized();
            users = users.filter(function (x) { return x.id !== idFromUrl(); });
            localStorage.setItem(usersKey, JSON.stringify(users));
            return ok();
        }
        // helper functions
        function ok(body) {
            return rxjs_1.of(new http_1.HttpResponse({ status: 200, body: body }))
                .pipe(operators_1.delay(500)); // delay observable to simulate server api call
        }
        function error(message) {
            return rxjs_1.throwError({ error: { message: message } })
                .pipe(operators_1.materialize(), operators_1.delay(500), operators_1.dematerialize()); // call materialize and dematerialize to ensure delay even if an error is thrown (https://github.com/Reactive-Extensions/RxJS/issues/648);
        }
        function unauthorized() {
            return rxjs_1.throwError({ status: 401, error: { message: 'Unauthorized' } })
                .pipe(operators_1.materialize(), operators_1.delay(500), operators_1.dematerialize());
        }
        function basicDetails(user) {
            var id = user.id, username = user.username, firstName = user.firstName, lastName = user.lastName;
            return { id: id, username: username, firstName: firstName, lastName: lastName };
        }
        function isLoggedIn() {
            return headers.get('Authorization') === 'Bearer fake-jwt-token';
        }
        function idFromUrl() {
            var urlParts = url.split('/');
            return parseInt(urlParts[urlParts.length - 1]);
        }
    };
    FakeBackendInterceptor = __decorate([
        core_1.Injectable()
    ], FakeBackendInterceptor);
    return FakeBackendInterceptor;
}());
exports.FakeBackendInterceptor = FakeBackendInterceptor;
exports.fakeBackendProvider = {
    // use fake backend in place of Http service for backend-less development
    provide: http_1.HTTP_INTERCEPTORS,
    useClass: FakeBackendInterceptor,
    multi: true
};
