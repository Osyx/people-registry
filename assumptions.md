# Assumptions

I assumed that:
* The main class will be implemented when the endpoints are implemented, since it depends on how you want to run that service.
* An actual DB will be used instead of the current simple in-memory variant when moving to production.
* Better error management will be implemented. Also better differentiation between what's actual errors and what's endpoint errors.
* Translation and string management will be handled later.
* The junior will handle almost everything in the PersonEndpoint class, I just provided examples of usage and naming.
* More validations in the validation class. E.g. validations and handling of supported SSN formats, together with other string validations and formatting to safeguard against SQL injection/XSS attacks etc.
