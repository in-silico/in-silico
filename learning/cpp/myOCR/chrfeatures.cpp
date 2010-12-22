
#include "chrfeatures.h"

using namespace MyOCR;

double ChrMoments::getRawMoment(int p, int q) {
    ImgMatrix *m = component->getMatrix();
    pixel *data = m->getData();
    double sum=0;
    for (int i=0; i<m->getHeight(); i++) {
        for (int j=0; j<m->getWidth(); j++) {
            sum += pow(i,p)*pow(j,q)*(*data);
            data++;
        }
    }
    return sum;
}

void ChrMoments::getCentroid(double *x, double *y) {
    *x = cx;
    *y = cy;
}

ChrMoments::ChrMoments(ConComponent* component) {
    this->component = component;
    double m00 = getRawMoment(0,0);
    double m10 = getRawMoment(1,0);
    double m01 = getRawMoment(0,1);
    cx = m10/m00;
    cy = m01/m00;
    u00 = m00;
}

double ChrMoments::getCenterMoment(int p, int q) {
    ImgMatrix *m = component->getMatrix();
    pixel *data = m->getData();
    double sum=0;
    for (int i=0; i<m->getHeight(); i++) {
        for (int j=0; j<m->getWidth(); j++) {
            sum += pow(i-cx,p)*pow(j-cy,q)*(*data);
            data++;
        }
    }
    return sum;
}

complex<double> ChrMoments::getComplexMoment(int p, int q) {
    ImgMatrix *m = component->getMatrix();
    pixel *data = m->getData();
    complex<double> sum(0, 0);
    complex<double> ii(0, 1.0);
    for (int i=0; i<m->getHeight(); i++) {
        for (int j=0; j<m->getWidth(); j++) {
            sum += pow((double)(i-cx)+(double)(j-cy)*ii,p)*pow((double)(i-cx)-(double)(j-cy)*ii,q)*(double)(*data);
            data++;
        }
    }
    return sum/pow(u00, 1 + ((double)p+q)/2);
}

double ChrMoments::getScaleInvMoment(int p, int q) {
    double den = pow(u00, 1 + ((double)p+q)/2);
    return getCenterMoment(p,q)/den;
}

void ChrMoments::getFlusserMoments(double* ans) {
    complex<double> c[5][5];
    for(int i = 0; i < 5; i++)
        for(int j = 0; j < 5; j++)
            c[i][j] = getComplexMoment(i, j);
    ans[0] = abs(c[1][1]);
    ans[1] = abs(c[2][1] * c[1][2]);
    complex<double> tmp = c[2][0] * pow(c[1][2], 2);
    ans[2] = real(tmp);
    ans[3] = imag(tmp);
    tmp = c[3][0] * pow(c[1][2], 3);
    ans[4] = real(tmp);
    ans[5] = imag(tmp);
    ans[6] = abs(c[2][2]);
    tmp = c[3][1] * pow(c[1][2], 2);
    ans[7] = real(tmp);
    ans[8] = imag(tmp);
    tmp = c[4][0] * pow(c[1][2], 2);
    ans[9] = real(tmp);
    ans[10] = imag(tmp);
}

void ChrMoments::getHuMoments(double* ans) {
    double n[4][4];
    double a,b,c,d;
    for (int i=0; i<4; i++)
        for (int j=0; j<4; j++)
            n[i][j] = getScaleInvMoment(i,j);
    ans[0] = n[0][2] + n[2][0];
    ans[1] = pow(n[2][0] - n[0][2],2) + pow(2*n[1][1],2);
    ans[2] = pow(n[3][0] - 3*n[1][2], 2) + pow(3*n[2][1] - n[0][3], 2);
    ans[3] = pow(n[3][0] + n[1][2], 2) + pow(n[2][1] + n[0][3], 2);

    a = (n[3][0]-3*n[1][2])*(n[3][0]+n[1][2]);
    b = pow(n[3][0]+n[1][2],2) - 3*pow(n[2][1]+n[0][3], 2);
    c = (3*n[2][1]-n[0][3])*(n[2][1]+n[0][3]);
    d = 3*pow(n[3][0]+n[1][2],2) - pow(n[2][1]+n[0][3], 2);
    ans[4] = a*b + c*d;

    a = (n[2][0]-n[0][2])*( pow(n[3][0]+n[1][2],2) - pow(n[2][1]+n[0][3], 2) );
    b = 4 * n[1][1] * (n[3][0] + n[1][2]) * (n[2][1] + n[0][3]);
    ans[5] = a + b;

    a = (3*n[2][1]-n[0][3])*(n[3][0]+n[1][2]);
    b = pow(n[3][0]+n[1][2],2) - 3*pow(n[2][1]+n[0][3], 2);
    c = (n[3][0]-3*n[1][2])*(n[2][1]+n[0][3]);
    d = 3*pow(n[3][0]+n[1][2],2) - pow(n[2][1]+n[0][3], 2);
    ans[6] = a*b - c*d;
}
