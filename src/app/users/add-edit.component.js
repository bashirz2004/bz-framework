"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AddEditComponent = void 0;
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var operators_1 = require("rxjs/operators");
var AddEditComponent = /** @class */ (function () {
    function AddEditComponent(formBuilder, route, router, accountService, alertService) {
        this.formBuilder = formBuilder;
        this.route = route;
        this.router = router;
        this.accountService = accountService;
        this.alertService = alertService;
        this.loading = false;
        this.submitted = false;
    }
    AddEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.id = this.route.snapshot.params['id'];
        this.isAddMode = !this.id;
        // password not required in edit mode
        var passwordValidators = [forms_1.Validators.minLength(2)];
        if (this.isAddMode) {
            passwordValidators.push(forms_1.Validators.required);
        }
        this.form = this.formBuilder.group({
            firstName: ['', forms_1.Validators.required],
            lastName: ['', forms_1.Validators.required],
            username: ['', forms_1.Validators.required],
            password: ['', passwordValidators]
        });
        if (!this.isAddMode) {
            this.accountService.getById(this.id)
                .pipe(operators_1.first())
                .subscribe(function (x) { return _this.form.patchValue(x); });
        }
    };
    Object.defineProperty(AddEditComponent.prototype, "f", {
        // convenience getter for easy access to form fields
        get: function () { return this.form.controls; },
        enumerable: false,
        configurable: true
    });
    AddEditComponent.prototype.onSubmit = function () {
        this.submitted = true;
        // reset alerts on submit
        this.alertService.clear();
        // stop here if form is invalid
        if (this.form.invalid) {
            return;
        }
        this.loading = true;
        if (this.isAddMode) {
            this.createUser();
        }
        else {
            this.updateUser();
        }
    };
    AddEditComponent.prototype.createUser = function () {
        var _this = this;
        this.accountService.register(this.form.value)
            .pipe(operators_1.first())
            .subscribe({
            next: function () {
                _this.alertService.success('User added successfully', { keepAfterRouteChange: true });
                _this.router.navigate(['../'], { relativeTo: _this.route });
            },
            error: function (error) {
                _this.alertService.error(error);
                _this.loading = false;
            }
        });
    };
    AddEditComponent.prototype.updateUser = function () {
        var _this = this;
        this.accountService.update(this.id, this.form.value)
            .pipe(operators_1.first())
            .subscribe({
            next: function () {
                _this.alertService.success('Update successful', { keepAfterRouteChange: true });
                _this.router.navigate(['../../'], { relativeTo: _this.route });
            },
            error: function (error) {
                _this.alertService.error(error);
                _this.loading = false;
            }
        });
    };
    AddEditComponent = __decorate([
        core_1.Component({ templateUrl: 'add-edit.component.html' })
    ], AddEditComponent);
    return AddEditComponent;
}());
exports.AddEditComponent = AddEditComponent;
