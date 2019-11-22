Feature: BDD Tests for CoinMarket APIs


  Scenario Outline: Retreve ID of the crypto currency and convert them into Bolivian Boliviano
    When I retrieve the ID of "<crypro currency>"
    Then I should get the successful response
    When I convert into Bolivian Boliviano using price Conversion API
    Then I should get the successful response
    Examples:
      | crypro currency |
      | BTC             |
      | USDT            |
      | ETH             |

  Scenario Outline: Validate various Etherium data
    Given I have to retrieve the Etherium info
    When I request the Etherium Info from 'Info' API for the crypto currency
    Then I should get the successful response
    And Response data should contain "<logo>","<technical doc>","<symbol>","<date added>","<platform>" and "<tags>"
    Examples:
      | logo                                                         | technical doc                                     | symbol | date added               | platform | tags     |
      | https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png | https://github.com/ethereum/wiki/wiki/White-Paper | ETH    | 2015-08-07T00:00:00.000Z | null     | mineable |


  Scenario: Verify top 10 minable crypto currencies
    Given I retrieve first 10 crypto currencies
    When I check for minable tags in the response
    Then correct minable crypto currencies are displayed