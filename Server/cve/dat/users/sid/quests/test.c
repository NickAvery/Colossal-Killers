#include 
#include 
#include 
int ComputeFactorial( int number )
{
  int fact=0 , j=0;

  for ( j = 0 ; j <= number ; j++ )
    fact = fact * j;

  return fact;
}

double ComputeSeriesValue( double x, int n )
{
  double seriesValue =0.0;
  int k = 0; 
  int factorial = -1;
  double xpow = 1;

  for( k = 0 ; k <= n ; k++ )
  {
    factorial = ComputeFactorial(k);
    seriesValue += ( xpow / factorial) ;

    xpow = xpow * x;
  }

  return seriesValue;
}

void main()
{
  double x=0;
  int n=0;

  double seriesValue=0.0;

  cout << "This program is used to compute the value of the folllowing series : " << endl;

  cout << "(x^0)/0! + (x^1)/1! + (x^2)/2! + (x^3)/3! + ... + (x^n)/n! " << endl;

  cout << "Please enter the value of x : " ;
  
  cin >> x ;

  cout << endl << "Please enter an integer value for n : " ;
  cin >> n;
  cout << endl;

  seriesValue = ComputeSeriesValue( x, n );

  cout << "The value of the series for the values entered is " 
	<< seriesValue << endl;
}
